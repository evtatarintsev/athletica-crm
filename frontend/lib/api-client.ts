import axios, { AxiosError, AxiosRequestConfig } from 'axios';
import {Configuration, DefaultApi, RefreshTokenRequest} from 'api_client';

const axiosInstance = axios.create({
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
                    failedQueue.push({ resolve, reject });
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
                const refreshTokenRequest: RefreshTokenRequest = {};
                await apiClient.refreshToken(refreshTokenRequest);

                // Если успешно, обрабатываем очередь и повторяем исходный запрос
                processQueue(null);
                isRefreshing = false;
                return axiosInstance(originalRequest);
            } catch (refreshError: any) {
                // Если обновление токена не удалось, перенаправляем на страницу логина
                processQueue(refreshError);
                isRefreshing = false;

                // Проверяем, является ли ошибка обновления токена 401 ошибкой
                const isRefreshTokenUnauthorized = refreshError?.response?.status === 401;

                // Всегда перенаправляем на страницу логина при ошибке обновления токена,
                // особенно важно при 401 ошибке от refreshToken
                redirectToLogin();

                console.error('Refresh token failed:', isRefreshTokenUnauthorized ? '401 Unauthorized' : refreshError);
                return Promise.reject(refreshError);
            }
        }

        return Promise.reject(error);
    }
);
