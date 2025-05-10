package ru.athletica.crm.modules.customers

import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.insert

/**
 * Модель клиента организации.
 */
class Customer(
    val id: CustomerId,
    val fullName: CustomerName,
) {
    /**
     * Сохраняет клиента в БД. Вызывать метод необходимо в контексте транзакции.
     */
    context(_: Transaction)
    fun save() {
        CustomersSqlTable.insert {
            it[id] = this@Customer.id.value
            it[fullName] = this@Customer.fullName.value
        }
    }
}