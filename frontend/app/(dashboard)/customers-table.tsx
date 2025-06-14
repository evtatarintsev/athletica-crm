'use client';

import {Table, TableBody, TableHead, TableHeader, TableRow} from '@/components/ui/table';
import {useState} from 'react';
import {Card, CardContent, CardFooter, CardHeader, CardTitle} from '@/components/ui/card';
import {CustomersTableRow} from './customers-table-row';
import {useRouter} from 'next/navigation';
import {ChevronLeft, ChevronRight, File} from 'lucide-react';
import {Button, Chip, Stack} from "@mui/material";
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import AddIcon from '@mui/icons-material/Add';
import {SearchInput} from "./search";
import {Customer} from "./customers/customer";

type StatusFilter = 'all' | 'active' | 'inactive' | 'archived';

interface CustomerTableProps {
    customers: Customer[];
    nextOffset: number;
    totalCustomers: number;
}

export function CustomersTable(props: CustomerTableProps) {
    let router = useRouter();
    const productsPerPage = 10;
    function prevPage() {
        router.back();
    }

    function nextPage() {
        router.push(`?offset=${props.nextOffset}`, {scroll: false});
    }

    return (
        <Card>
            <CardHeader className="flex flex-row items-center justify-between">
                <CardTitle>Клиенты</CardTitle>
                <div className="flex gap-2">
                    <Button variant="outlined" size="small" className="hidden md:block">
                        <FileDownloadIcon className="h-3.5 w-3.5"/>
                        <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">Выгрузить</span>
                    </Button>
                    <Button
                        variant="outlined"
                        size="small"
                        className="hidden md:block"
                        onClick={() => router.push('/customers/add')}
                    >
                        <AddIcon className="h-3.5 w-3.5 mr-1"/>
                        Добавить клиента
                    </Button>
                </div>
            </CardHeader>
            <CardContent>
                <div className="flex justify-between items-center">
                    <Stack direction="row" spacing={1}>
                        <Chip label="Основной"/>
                        <Chip label="Отключенный" disabled/>
                        <Chip label="Кликабельный" onClick={() => {
                        }}/>
                        <Chip label="Удаляемый" onDelete={() => {
                        }}/>
                        <Chip label="Фильтр" icon={<AddIcon/>}/>
                    </Stack>
                    <Stack direction="row" spacing={1}>
                        <SearchInput/>
                    </Stack>
                </div>

                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead className="hidden w-[100px] sm:table-cell">
                                <span className="sr-only">Image</span>
                            </TableHead>
                            <TableHead>Имя</TableHead>
                            <TableHead>Статус</TableHead>
                            <TableHead className="hidden md:table-cell">Телефон</TableHead>
                            <TableHead className="hidden md:table-cell">ДР</TableHead>
                            <TableHead>
                                <span className="sr-only">Действия</span>
                            </TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {props.customers
                            .map((customer) => (
                                <CustomersTableRow key={customer.id} customer={customer}/>
                            ))}
                    </TableBody>
                </Table>
            </CardContent>
            <CardFooter>
                <form className="flex items-center w-full justify-between">
                    <div className="text-xs text-muted-foreground">
                        Показано{' '}
                        <strong>
                            {Math.max(0, Math.min(props.nextOffset - productsPerPage, props.totalCustomers) + 1)}-{props.nextOffset}
                        </strong>{' '}
                        из <strong>{props.totalCustomers}</strong> клиентов
                    </div>
                    <div className="flex">
                        <Button
                            formAction={prevPage}
                            variant="text"
                            size="small"
                            type="submit"
                            className="hover:bg-accent hover:text-accent-foreground"
                            disabled={props.nextOffset === productsPerPage}
                        >
                            <ChevronLeft className="mr-2 h-4 w-4"/>
                            Назад
                        </Button>
                        <Button
                            formAction={nextPage}
                            variant="text"
                            size="small"
                            type="submit"
                            className="hover:bg-accent hover:text-accent-foreground"
                            disabled={props.nextOffset + productsPerPage > props.totalCustomers}
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
