import {getProducts} from "@/lib/db";
import {Tabs, TabsContent, TabsList, TabsTrigger} from "@/components/ui/tabs";
import {Button} from "@/components/ui/button";
import {File, PlusCircle} from "lucide-react";
import {CustomersTable} from "../customers-table";


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
                    <Button size="sm" variant="outline" className="h-8 gap-1">
                        <File className="h-3.5 w-3.5"/>
                        <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">Выгрузить</span>
                    </Button>
                    <Button size="sm" className="h-8 gap-1">
                        <PlusCircle className="h-3.5 w-3.5"/>
                        <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">Добавить</span>
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
