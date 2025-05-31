'use client'

export default async function fetcher<T>(url: string): Promise<T> {
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error('Ошибка сети');
    }
    return response.json();
}
