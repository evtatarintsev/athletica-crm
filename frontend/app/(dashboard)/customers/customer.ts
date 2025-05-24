/**
 * Модель клиента в контектсте работы со списком клиентов.
 */
export interface Customer {
    /** Уникальный UUID клиента */
    id: string;
    /** URL фото клиента */
    imageUrl: string;
    /** Имя клиента */
    name: string;
    /** Состояние клиента  */
    status: 'active' | 'inactive' | 'archived';
    /** Номер телефона клиента если указан, иначе null */
    phone_no: string | null;

    price: number;
    stock: number;
    availableAt: Date;
}
