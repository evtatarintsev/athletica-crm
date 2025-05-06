
export interface IProduct {
  id: number;
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
  const moreProducts: IProduct[] = [
    {
      id: 1,
      imageUrl: "https://placehold.co/400x300/png",
      name: "Premium Headphones",
      status: "active",
      price: 199.99,
      stock: 50,
      availableAt: new Date("2024-01-01")
    },
    {
      id: 2,
      imageUrl: "https://placehold.co/400x300/png",
      name: "Wireless Mouse",
      status: "active",
      price: 49.99,
      stock: 100,
      availableAt: new Date("2024-01-15")
    },
    {
      id: 3,
      imageUrl: "https://placehold.co/400x300/png",
      name: "Mechanical Keyboard",
      status: "inactive",
      price: 129.99,
      stock: 25,
      availableAt: new Date("2024-02-01")
    },
    {
      id: 4,
      imageUrl: "https://placehold.co/400x300/png",
      name: "Gaming Monitor",
      status: "active",
      price: 299.99,
      stock: 30,
      availableAt: new Date("2024-01-20")
    },
    {
      id: 5,
      imageUrl: "https://placehold.co/400x300/png",
      name: "USB-C Hub",
      status: "archived",
      price: 79.99,
      stock: 0,
      availableAt: new Date("2024-01-10")
    }
  ];

  return {
    products: moreProducts,
    newOffset: null,
    totalProducts: 5
  };
}

export async function deleteProductById(id: number) {

}
