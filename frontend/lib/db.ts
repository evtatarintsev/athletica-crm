import { DefaultApi } from '@athletica/client';

export interface IProduct {
  id: string;
  imageUrl: string;
  name: string;
  status: 'active' | 'inactive' | 'archived';
  price: number;
  stock: number;
  availableAt: Date;
}

export async function getProducts(
  search: string,
  offset: number
): Promise<{
  products: IProduct[];
  newOffset: number | null;
  totalProducts: number;
}> {
  const api = new DefaultApi();
  const response = await api.getCustomersList({
    limit: 10,
    offset: offset
  });

  // Map CustomerInList to IProduct
  const products: IProduct[] = response.customers.map((customer, index) => ({
    id: customer.id,
    imageUrl: "https://placehold.co/400x300/png", // Default image
    name: customer.fullName,
    status: "active" as const, // Default status
    price: 99.99, // Default price
    stock: 10, // Default stock
    availableAt: customer.birthday || new Date()
  }));

  return {
    products,
    newOffset: response.hasMore ? offset + 10 : null,
    totalProducts: response.totalCount
  };
}
