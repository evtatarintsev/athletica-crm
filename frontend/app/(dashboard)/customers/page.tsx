'use client';

import {CustomersTable} from "../customers-table";
import {useEffect, useState} from 'react';
import {Spinner} from '@/components/icons';
import {useSearchParams} from 'next/navigation';
import {apiClient} from "@/lib/api-client";
import {Customer} from "./customer";

export default function CustomersPage() {
    const searchParams = useSearchParams();
    const search = searchParams.get('q') ?? '';
    const offsetParam = searchParams.get('offset') ?? '0';
    const [loading, setLoading] = useState(true);
    const [productsData, setProductsData] = useState<{
        customers: Customer[];
        newOffset: number | null;
        totalCustomers: number;
    }>({
        customers: [],
        newOffset: 0,
        totalCustomers: 0
    });

    useEffect(() => {
        const fetchProducts = async () => {
            setLoading(true);
            try {
                const data = await getCustomers(search, Number(offsetParam));
                setProductsData(data);
            } catch (error) {
                console.error('Error fetching products:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchProducts();
    }, [search, offsetParam]);

    return (
        <>
            {loading ? (
                <div className="flex justify-center items-center h-64">
                    <Spinner/>
                </div>
            ) : (
                <CustomersTable
                    customers={productsData.customers}
                    offset={productsData.newOffset ?? 0}
                    totalCustomers={productsData.totalCustomers}
                />
            )}
        </>
    );
}


async function getCustomers(search: string, offset: number): Promise<{
    customers: Customer[];
    newOffset: number | null;
    totalCustomers: number;
}> {
    const response = await apiClient.getCustomersList({
        limit: 10,
        offset: offset
    });

    const customers: Customer[] = response.customers.map(customer => ({
        id: customer.id,
        imageUrl: "https://placehold.co/400x300/png", // Default image
        name: customer.fullName,
        status: "active" as const,
        price: 99.99,
        stock: 10,
        availableAt: customer.birthday || new Date()
    }));

    return {
        customers: customers,
        newOffset: response.hasMore ? offset + 10 : null,
        totalCustomers: response.totalCount
    };
}
