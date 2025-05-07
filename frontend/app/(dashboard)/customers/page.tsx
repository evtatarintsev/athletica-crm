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
        <CustomersTable
            products={products}
            offset={newOffset ?? 0}
            totalProducts={totalProducts}
        />
    );
}
