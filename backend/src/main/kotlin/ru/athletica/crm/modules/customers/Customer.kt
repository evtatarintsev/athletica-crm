package ru.athletica.crm.modules.customers

import org.jetbrains.exposed.sql.insert
import ru.athletica.crm.db.suspendTransaction


class Customer(
    val id: CustomerId,
    val fullName: CustomerName,
) {
    suspend fun save(): Unit = suspendTransaction {
        CustomersSqlTable.insert {
            it[id] = this@Customer.id.value
            it[fullName] = this@Customer.fullName.value
        }
    }
}