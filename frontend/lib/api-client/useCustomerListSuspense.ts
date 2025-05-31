import useSWR from "swr";

interface ClientListResponse {

    /**
     * Всего записей
     */
    totalCount: number;

    /**
     * Есть ли еще клиенты
     */
    hasMore: boolean;

    /**
     * Список клиентов
     */
    customers: CustomerInList[]
}

interface CustomerInList {
    id: string;
    fullName: string;
    phone?: string;
    birthday?: Date
}

export default function useCustomerListSuspense(limit: number, offset: number): ClientListResponse {
    const {data} = useSWR(`/api/customers?limit=${limit}&offset=${offset}`, {suspense: true});
    return data as ClientListResponse;
}
