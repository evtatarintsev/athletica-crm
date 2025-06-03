import axios from 'axios';
import {Configuration, DefaultApi} from 'api_client';

const axiosInstance = axios.create({
    baseURL: process.env.NEXT_PUBLIC_API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

axiosInstance.interceptors.request.use((config) => {
    const token = localStorage.getItem('jwt');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

const config = new Configuration({
    baseOptions: {
        baseURL: process.env.NEXT_PUBLIC_API_URL,
    },
});

export const apiClient = new DefaultApi(config, undefined, axiosInstance);
