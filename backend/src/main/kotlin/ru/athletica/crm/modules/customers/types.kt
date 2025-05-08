@file:OptIn(ExperimentalUuidApi::class)

package ru.athletica.crm.modules.customers

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@JvmInline
value class CustomerId(private val value: Uuid){
    constructor() : this(Uuid.random())
}

@JvmInline
value class CustomerName(private val value: String)

fun String.toCustomerName() = CustomerName(this)