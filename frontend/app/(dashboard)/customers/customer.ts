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

    price: number;
    stock: number;
    availableAt: Date;
}