package ru.athletica.crm.modules.customers

import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.selectAll

class Customers {
    context(_: Transaction)
    fun byId(id: CustomerId) = CustomersSqlTable
        .selectAll()
        .where { CustomersSqlTable.id.eq(id.value) }
        .map {
            Customer(
                CustomerId(it[CustomersSqlTable.id]),
                CustomerName(it[CustomersSqlTable.fullName])
            )
        }.first()
}
