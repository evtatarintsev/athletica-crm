package ru.athletica.crm.modules.customers

import java.util.UUID

/**
 * Id клиента.
 */
@JvmInline
value class CustomerId(val value: UUID){
    /**
     * Новый случайный id клиента.
     */
    constructor() : this(UUID.randomUUID())
}


/**
 * Имя клиента.
 */
@JvmInline
value class CustomerName(val value: String)

fun String.toCustomerName() = CustomerName(this)