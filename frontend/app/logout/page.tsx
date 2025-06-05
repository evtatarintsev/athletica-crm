'use client';

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { mutate } from 'swr';

export default function LogoutPage() {
  const router = useRouter();

  useEffect(() => {
    // Очищаем токены аутентификации из localStorage
    if (typeof window !== 'undefined') {
      localStorage.removeItem('jwt');
      localStorage.removeItem('refreshToken');
    }

    // Очищаем кэш SWR
    mutate(() => true, undefined, { revalidate: false });

    // Перенаправляем на страницу логина
    router.push('/login');
  }, [router]);

  // Возвращаем пустой компонент, так как перенаправление происходит в useEffect
  return null;
}
