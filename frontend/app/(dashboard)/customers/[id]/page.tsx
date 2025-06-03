'use client';

import { Suspense } from 'react';
import { Spinner } from '@/components/icons';
import { useParams } from 'next/navigation';
import ErrorBoundary from "@/components/error-boundary";
import useCustomerDetailsSuspense from "@/lib/swr/useCustomerDetailsSuspense";

function CustomerDetailsContent() {
    const params = useParams();
    const id = params.id as string;
    
    const customer = useCustomerDetailsSuspense(id);

    return (
        <div className="space-y-6">
            <div className="flex justify-between items-center">
                <h1 className="text-2xl font-bold">{customer.fullName}</h1>
                <div className="flex space-x-2">
                    <button className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600">
                        Edit
                    </button>
                </div>
            </div>
            
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div className="bg-white p-6 rounded-lg shadow">
                    <h2 className="text-xl font-semibold mb-4">Contact Information</h2>
                    <div className="space-y-3">
                        <div>
                            <p className="text-sm text-gray-500">Full Name</p>
                            <p>{customer.fullName}</p>
                        </div>
                        {customer.phone && (
                            <div>
                                <p className="text-sm text-gray-500">Phone</p>
                                <p>{customer.phone}</p>
                            </div>
                        )}
                        {customer.email && (
                            <div>
                                <p className="text-sm text-gray-500">Email</p>
                                <p>{customer.email}</p>
                            </div>
                        )}
                        {customer.birthday && (
                            <div>
                                <p className="text-sm text-gray-500">Birthday</p>
                                <p>{customer.birthday}</p>
                            </div>
                        )}
                    </div>
                </div>
                
                <div className="bg-white p-6 rounded-lg shadow">
                    <h2 className="text-xl font-semibold mb-4">Additional Information</h2>
                    <div className="space-y-3">
                        {customer.address && (
                            <div>
                                <p className="text-sm text-gray-500">Address</p>
                                <p>{customer.address}</p>
                            </div>
                        )}
                        {customer.notes && (
                            <div>
                                <p className="text-sm text-gray-500">Notes</p>
                                <p>{customer.notes}</p>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default function CustomerDetailsPage() {
    return (
        <ErrorBoundary fallback={<div>Что-то пошло не так</div>}>
            <Suspense fallback={<div className="flex justify-center items-center h-64"><Spinner/></div>}>
                <CustomerDetailsContent />
            </Suspense>
        </ErrorBoundary>
    );
}