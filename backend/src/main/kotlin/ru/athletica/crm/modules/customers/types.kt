package ru.athletica.crm.modules.customers

import java.util.UUID


@JvmInline
value class CustomerId(val value: UUID){
    constructor() : this(UUID.randomUUID())
}

@JvmInline
value class CustomerName(val value: String)

fun String.toCustomerName() = CustomerName(this)