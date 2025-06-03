import useSWR from "swr";
import {apiClient} from "@/lib/api-client";
import {CustomerListResponse} from "api_client";

/**
 * Получение списка клиентов
 */
export default function useCustomerListSuspense(limit: number, offset: number): CustomerListResponse {
    const fetcher = () => apiClient.getCustomersList(limit, offset);
    const {data} = useSWR(`getCustomersList`, fetcher, {suspense: true});
    return data.data as CustomerListResponse;
}
