/**
 * Модель клиента в контексте работы со списком клиентов.
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
    phone_no?: string;
    /** День рождения клиента **/
    birthday?: Date;
}
