'use client';

import {getProducts, IProduct} from "@/lib/db";
import {Tabs, TabsContent, TabsList, TabsTrigger} from "@/components/ui/tabs";
import {CustomersTable} from "../customers-table";
import {Button} from "@mui/material";
import FileDownload from '@mui/icons-material/FileDownload';
import { useState, useEffect } from 'react';
import { Spinner } from '@/components/icons';
import { useSearchParams } from 'next/navigation';

export default function CustomersPage() {
    const searchParams = useSearchParams();
    const search = searchParams.get('q') ?? '';
    const offsetParam = searchParams.get('offset') ?? '0';
    const [loading, setLoading] = useState(true);
    const [productsData, setProductsData] = useState<{
        products: IProduct[];
        newOffset: number | null;
        totalProducts: number;
    }>({
        products: [],
        newOffset: 0,
        totalProducts: 0
    });

    useEffect(() => {
        const fetchProducts = async () => {
            setLoading(true);
            try {
                const data = await getProducts(search, Number(offsetParam));
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
                    <Spinner />
                </div>
            ) : (
                <CustomersTable
                    products={productsData.products}
                    offset={productsData.newOffset ?? 0}
                    totalProducts={productsData.totalProducts}
                />
            )}
        </>
    );
}
