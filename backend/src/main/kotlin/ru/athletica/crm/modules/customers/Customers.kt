package ru.athletica.crm.modules.customers

import org.jetbrains.exposed.sql.selectAll
import ru.athletica.crm.db.suspendTransaction

class Customers {
    suspend fun byId(id: CustomerId): Customer = suspendTransaction {
        CustomersSqlTable
            .selectAll()
            .where { CustomersSqlTable.id.eq(id.value) }
            .map {
                Customer(
                    CustomerId(it[CustomersSqlTable.id]),
                    CustomerName(it[CustomersSqlTable.fullName])
                )
            }.first()
    }
}
