import useSWR from "swr";
import {apiClient} from "@/lib/api-client";
import {CustomerDetails} from "api_client";

/**
 * Получение детальной информации о клиенте
 */
export default function useCustomerDetailsSuspense(id: string): CustomerDetails {
    const fetcher = () => apiClient.getCustomerDetails(id);
    const {data} = useSWR(`getCustomerDetails-${id}`, fetcher, {suspense: true});
    return data.data as CustomerDetails;
}