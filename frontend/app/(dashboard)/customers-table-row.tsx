import Image from 'next/image';
import { Badge } from '@/components/ui/badge';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger
} from '@/components/ui/dropdown-menu';
import { MoreHorizontal } from 'lucide-react';
import { TableCell, TableRow } from '@/components/ui/table';
import { deleteCustomer } from './actions';
import Button from '@mui/material/Button';
import {Customer} from "./customers/customer";

export function CustomersTableRow({ customer }: { customer: Customer}) {
  return (
    <TableRow>
      <TableCell className="hidden sm:table-cell">
        <Image
          alt="Product image"
          className="aspect-square rounded-md object-cover"
          height="64"
          src={customer.imageUrl}
          width="64"
        />
      </TableCell>
      <TableCell className="font-medium">{customer.name}</TableCell>
      <TableCell>
        <Badge variant="outline" className="capitalize">
          {customer.status}
        </Badge>
      </TableCell>
      <TableCell className="hidden md:table-cell">{`$${customer.price}`}</TableCell>
      <TableCell className="hidden md:table-cell">{customer.stock}</TableCell>
      <TableCell className="hidden md:table-cell">{customer.phone_no}</TableCell>
      <TableCell className="hidden md:table-cell">
        {customer.availableAt.toLocaleDateString("en-US")}
      </TableCell>
      <TableCell>
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button aria-haspopup="true" size="small" variant="outlined">
              <MoreHorizontal className="h-4 w-4" />
              <span className="sr-only">Toggle menu</span>
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Действия</DropdownMenuLabel>
            <DropdownMenuItem>Изменить</DropdownMenuItem>
            <DropdownMenuItem>
              <form action={deleteCustomer}>
                <button type="submit">Удалить</button>
              </form>
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </TableCell>
    </TableRow>
  );
}
