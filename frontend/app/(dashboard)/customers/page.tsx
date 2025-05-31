'use client';

import {CustomersTable} from "../customers-table";
import {Suspense} from 'react';
import {Spinner} from '@/components/icons';
import {useSearchParams} from 'next/navigation';
import {Customer} from "./customer";
import useCustomerListSuspense from "@/lib/api-client/useCustomerListSuspense";
import ErrorBoundary from "@/components/error-boundary";

function CustomersContent() {
    const searchParams = useSearchParams();
    // const search = searchParams.get('q') ?? '';
    const customerPerPage = 10;
    const offset = Number(searchParams.get('offset') ?? '0');

    const data = useCustomerListSuspense(customerPerPage, offset);

    const customers: Customer[] = data.customers.map(
        customer => ({
            id: customer.id,
            imageUrl: "https://placehold.co/400x300/png",
            name: customer.fullName,
            status: "active",
            phone_no: customer.phone,
            birthday: customer.birthday
        })
    );

    return (
        <CustomersTable
            customers={customers}
            nextOffset={data.hasMore ? offset + 10 : 0}
            totalCustomers={data.totalCount}
        />
    );
}

export default function CustomersPage() {
    return (
        <ErrorBoundary fallback={<div>Что-то пошло не так</div>}>
            <Suspense fallback={<div className="flex justify-center items-center h-64"><Spinner/></div>}>
                <CustomersContent/>
            </Suspense>
        </ErrorBoundary>
    );
}
