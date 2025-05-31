package ru.athletica.crm.api.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.athletica.crm.api.schemas.CustomerInList
import ru.athletica.crm.api.schemas.CustomerListResponse
import ru.athletica.crm.db.suspendTransaction
import ru.athletica.crm.modules.customers.Customer
import ru.athletica.crm.modules.customers.Customers

data class GetCustomersParams(
    val limit: Int = 10,
    val offset: Int = 0
)


@RestController
@RequestMapping("/api/customers")
class CustomersList(private val customers: Customers) {


    @GetMapping
    suspend fun getCustomers(params: GetCustomersParams): CustomerListResponse = suspendTransaction {
        val result = customers.list(Customers.ListRequest(params.limit, params.offset.toLong()))

        return@suspendTransaction CustomerListResponse(
            customers = result.customers.map { it.toCustomerInList() },
            hasMore = params.offset + params.limit < result.totalCount,
            totalCount = result.totalCount.toInt()
        )
    }

    fun Customer.toCustomerInList() = CustomerInList(
        id = id.value,
        fullName = fullName.value,
        phone = phone,
    )
}
