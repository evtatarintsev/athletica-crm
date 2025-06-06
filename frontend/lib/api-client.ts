import axios, {AxiosError, AxiosRequestConfig} from 'axios';
import {Configuration, DefaultApi} from 'api_client';

const axiosInstance = axios.create({
    baseURL: process.env.NEXT_PUBLIC_API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});
const axiosRefreshTokenInstance = axios.create({
    baseURL: process.env.NEXT_PUBLIC_API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});
axiosInstance.interceptors.request.use((config) => {
    return config;
});

const config = new Configuration({
    baseOptions: {
        baseURL: process.env.NEXT_PUBLIC_API_URL,
    },
});

// Создаем экземпляр API клиента
export const apiClient = new DefaultApi(config, undefined, axiosInstance);

// Для обновления токена используем отдельный клиент без настроенных interceptors
const apiRefreshTokenClient = new DefaultApi(config, undefined, axiosRefreshTokenInstance);

// Флаг для отслеживания процесса обновления токена
let isRefreshing = false;
// Очередь запросов, ожидающих обновления токена
let failedQueue: any[] = [];

// Обработка очереди запросов
const processQueue = (error: any, token: string | null = null) => {
    failedQueue.forEach(prom => {
        if (error) {
            prom.reject(error);
        } else {
            prom.resolve(token);
        }
    });

    failedQueue = [];
};

// Перенаправление на страницу логина
const redirectToLogin = () => {
    if (typeof window !== 'undefined') {
        window.location.href = '/login';
    }
};

// Добавляем перехватчик ответов для обработки 401 ошибок
axiosInstance.interceptors.response.use(
    response => response,
    async (error: AxiosError) => {
        const originalRequest = error.config as AxiosRequestConfig & { _retry?: boolean };
        // Проверяем, что ошибка 401 и запрос еще не повторялся
        if (error.response?.status === 401 && !originalRequest._retry) {
            if (isRefreshing) {
                // Если токен уже обновляется, добавляем запрос в очередь
                return new Promise((resolve, reject) => {
                    failedQueue.push({resolve, reject});
                })
                    .then(() => {
                        return axiosInstance(originalRequest);
                    })
                    .catch(err => {
                        return Promise.reject(err);
                    });
            }

            originalRequest._retry = true;
            isRefreshing = true;

            try {
                // Пытаемся обновить токен
                await apiRefreshTokenClient.refreshToken();

                // Если успешно, обрабатываем очередь и повторяем исходный запрос
                processQueue(null);

                // Повторяем исходный запрос с обновленным токеном
                return axiosInstance(originalRequest);
            } catch (refreshError: any) {
                // Если обновление токена не удалось, обрабатываем очередь с ошибкой
                processQueue(refreshError);
                // Перенаправляем на страницу логина при ошибке обновления токена
                redirectToLogin();
                return Promise.reject(refreshError);
            } finally {
                isRefreshing = false; // Убедимся, что флаг сброшен в любом случае
            }
        }

        return Promise.reject(error);
    }
);
