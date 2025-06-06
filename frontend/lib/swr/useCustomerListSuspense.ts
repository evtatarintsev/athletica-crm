import useSWR from "swr";
import {apiClient} from "@/lib/api-client";
import {CustomerListResponse} from "api_client";

/**
 * Получение списка клиентов
 */
export default function useCustomerListSuspense(limit: number, offset: number): CustomerListResponse {
    const fetcher = () => apiClient.getCustomersList(limit, offset);
    const fallbackData = {
        data: {
            customers: [],
            hasMore: false,
            totalCount: 0
        }
    };
    const {data} = useSWR("getCustomersList", fetcher, {
        suspense: true,
        fallback: {"getCustomersList": fallbackData}
    });
    return data.data as CustomerListResponse;
}
