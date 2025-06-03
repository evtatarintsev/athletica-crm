package ru.athletica.crm.modules.customers

import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.insert

/**
 * Модель клиента организации.
 */
class Customer(
    val id: CustomerId,
    val fullName: CustomerName,
    val phone: String?,
) {
    /**
     * Сохраняет клиента в БД.
     */
    context(_: Transaction)
    fun save() {
        CustomersSqlTable.insert {
            it[id] = this@Customer.id.value
            it[fullName] = this@Customer.fullName.value
            it[phone] = this@Customer.phone
        }
    }
}