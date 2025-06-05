'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { mutate } from 'swr';
import { apiClient } from '@/lib/api-client';
import { CircularProgress } from '@mui/material';

export default function LogoutPage() {
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const performLogout = async () => {
      try {
        // Вызываем API для выхода из системы
        await apiClient.logout();

        // Очищаем кэш SWR
        mutate(() => true, undefined, { revalidate: false });

        // Перенаправляем на страницу логина после успешного выхода
        router.push('/login');
      } catch (error) {
        console.error('Ошибка при выходе из системы:', error);
        // В случае ошибки все равно перенаправляем на страницу логина
        router.push('/login');
      } finally {
        setIsLoading(false);
      }
    };

    performLogout();
  }, [router]);

  // Показываем индикатор загрузки пока выполняется запрос
  if (isLoading) {
    return (
      <div className="min-h-screen flex justify-center items-center">
        <CircularProgress />
      </div>
    );
  }

  // Возвращаем пустой компонент, так как перенаправление происходит в useEffect
  return null;
}
