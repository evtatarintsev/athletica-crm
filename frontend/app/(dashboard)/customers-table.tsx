'use client';

import {
    TableHead,
    TableRow,
    TableHeader,
    TableBody,
    Table
} from '@/components/ui/table';
import {Badge} from '@/components/ui/badge';
import {useState} from 'react';
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle
} from '@/components/ui/card';
import {Product} from './product';
import {IProduct} from '@/lib/db';
import {useRouter} from 'next/navigation';
import {ChevronLeft, ChevronRight} from 'lucide-react';
import {Button} from '@/components/ui/button';
import {Chip, Stack} from "@mui/material";

type StatusFilter = 'all' | 'active' | 'inactive' | 'archived';

export function CustomersTable({
                                   products,
                                   offset,
                                   totalProducts
                               }: {
    products: IProduct[];
    offset: number;
    totalProducts: number;
}) {
    let router = useRouter();
    let productsPerPage = 5;
    const [activeFilter, setActiveFilter] = useState<StatusFilter>('all');

    const handleFilterChange = (filter: StatusFilter) => {
        setActiveFilter(filter);
    };

    function prevPage() {
        router.back();
    }

    function nextPage() {
        router.push(`/?offset=${offset}`, {scroll: false});
    }

    return (
        <Card>
            <CardHeader className="flex flex-row items-center justify-between">
                <CardTitle>Клиенты</CardTitle>
                <div className="flex gap-2">
                    <Button variant="outline" size="sm" className="hidden md:block">Export</Button>
                    <Button size="sm" className="hidden md:block">Create</Button>
                </div>
            </CardHeader>
            <CardContent>
                <Stack direction="row" spacing={1}>
                    <Chip label="Основной" />
                    <Chip label="Отключенный" disabled />
                    <Chip label="Кликабельный" onClick={() => alert('Chip нажат!')} />
                    <Chip
                        label="Удаляемый"
                        onDelete={() => alert('Chip удален!')}
                    />
                    <Chip label="Кликабельный Удаляемый" onClick={() => alert('Chip нажат!')} onDelete={() => alert('Chip удален!')} />
                </Stack>

                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead className="hidden w-[100px] sm:table-cell">
                                <span className="sr-only">Image</span>
                            </TableHead>
                            <TableHead>Имя</TableHead>
                            <TableHead>Статус</TableHead>
                            <TableHead className="hidden md:table-cell">Баланс</TableHead>
                            <TableHead className="hidden md:table-cell">
                                Группа
                            </TableHead>
                            <TableHead className="hidden md:table-cell">ДР</TableHead>
                            <TableHead>
                                <span className="sr-only">Действия</span>
                            </TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {products
                            .filter(product => activeFilter === 'all' || product.status === activeFilter)
                            .map((product) => (
                                <Product key={product.id} product={product}/>
                        ))}
                    </TableBody>
                </Table>
            </CardContent>
            <CardFooter>
                <form className="flex items-center w-full justify-between">
                    <div className="text-xs text-muted-foreground">
                        Показано{' '}
                        <strong>
                            {Math.max(0, Math.min(offset - productsPerPage, totalProducts) + 1)}-{offset}
                        </strong>{' '}
                        из <strong>{totalProducts}</strong> клиентов
                    </div>
                    <div className="flex">
                        <Button
                            formAction={prevPage}
                            variant="ghost"
                            size="sm"
                            type="submit"
                            disabled={offset === productsPerPage}
                        >
                            <ChevronLeft className="mr-2 h-4 w-4"/>
                            Назад
                        </Button>
                        <Button
                            formAction={nextPage}
                            variant="ghost"
                            size="sm"
                            type="submit"
                            disabled={offset + productsPerPage > totalProducts}
                        >
                            Вперед
                            <ChevronRight className="ml-2 h-4 w-4"/>
                        </Button>
                    </div>
                </form>
            </CardFooter>
        </Card>
    );
}
