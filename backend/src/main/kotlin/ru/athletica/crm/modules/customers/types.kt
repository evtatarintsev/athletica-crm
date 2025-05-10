package ru.athletica.crm.modules.customers

import java.util.UUID


@JvmInline
value class CustomerId(val value: UUID){
    /**
     * Новый случайный id клиента.
     */
    constructor() : this(UUID.randomUUID())
}

@JvmInline
value class CustomerName(val value: String)

fun String.toCustomerName() = CustomerName(this)