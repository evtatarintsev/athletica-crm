package ru.athletica.crm.modules.customers

import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component


@Component
class Customers {
    data class ListRequest(val limit: Int, val offset: Long)
    data class ListResponse(val customers: List<Customer>, val totalCount: Long)

    context(_: Transaction)
    fun list(request: ListRequest): ListResponse {
        val customers = CustomersSqlTable
            .selectAll()
            .limit(request.limit)
            .offset(request.offset)
            .map {
                Customer(
                    CustomerId(it[CustomersSqlTable.id]),
                    CustomerName(it[CustomersSqlTable.fullName]),
                    it[CustomersSqlTable.phone],
                )
            }

        val totalCount = CustomersSqlTable.selectAll().count()

        return ListResponse(customers, totalCount)
    }

    context(_: Transaction)
    fun byId(id: CustomerId): Customer = CustomersSqlTable
        .selectAll()
        .where { CustomersSqlTable.id.eq(id.value) }
        .map {
            Customer(
                CustomerId(it[CustomersSqlTable.id]),
                CustomerName(it[CustomersSqlTable.fullName]),
                null,
            )
        }.first()

    data class AddRequest(val fullName: String, val phone: String?)
    data class AddResponse(val id: CustomerId, val fullName: CustomerName, val phone: String?)

    context(_: Transaction)
    fun add(request: AddRequest): AddResponse {
        val id = CustomerId()
        val fullName = CustomerName(request.fullName)
        val customer = Customer(id, fullName, request.phone)
        customer.save()
        return AddResponse(id, fullName, request.phone)
    }
}
