package ru.athletica.crm.modules.customers

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class Customer(
    val id: CustomerId,
    val fullName: CustomerName,
) {
    suspend fun save(){
        transaction {
            CustomersSqlTable.insert {
                it[id] = id
                it[fullName] = fullName
            }
        }
    }
}