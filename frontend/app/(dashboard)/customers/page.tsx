import {getProducts} from "@/lib/db";
import {Tabs, TabsContent, TabsList, TabsTrigger} from "@/components/ui/tabs";
import {CustomersTable} from "../customers-table";
import {Button} from "@mui/material";
import FileDownload from '@mui/icons-material/FileDownload';

export default async function CustomersPage(
    props: {
        searchParams: Promise<{ q: string; offset: string }>;
    }
) {
    const searchParams = await props.searchParams;
    const search = searchParams.q ?? '';
    const offset = searchParams.offset ?? 0;
    const {products, newOffset, totalProducts} = await getProducts(
        search,
        Number(offset)
    );

    return (
        <Tabs defaultValue="all">
            <div className="flex items-center">
                <TabsList>
                    <TabsTrigger value="all">Все</TabsTrigger>
                    <TabsTrigger value="draft">Должники</TabsTrigger>
                    <TabsTrigger value="archived" className="hidden sm:flex">
                        Добавить фильтр
                    </TabsTrigger>
                </TabsList>
                <div className="ml-auto flex items-center gap-2">
                    <Button size="small" variant="outlined">
                        <FileDownload sx={{height: '14px', width: '14px'}}/>
                        <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">Выгрузить</span>
                    </Button>
                </div>
            </div>
            <TabsContent value="all">
                <CustomersTable
                    products={products}
                    offset={newOffset ?? 0}
                    totalProducts={totalProducts}
                />
            </TabsContent>
        </Tabs>
    );
}
