package ru.athletica.crm.api.customers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.athletica.api.schemas.AddCustomerRequest
import ru.athletica.api.schemas.AddCustomerResponse
import ru.athletica.crm.db.suspendTransaction
import ru.athletica.crm.modules.customers.Customers

@RestController
@RequestMapping("/api/customers/add")
class AddCustomer(private val customers: Customers) {

    @PostMapping
    suspend fun addCustomer(@RequestBody request: AddCustomerRequest): AddCustomerResponse = suspendTransaction {
        val result = customers.add(
            Customers.AddRequest(
                fullName = request.fullName,
                phone = request.phone
            )
        )

        return@suspendTransaction AddCustomerResponse(
            id = result.id.value,
            fullName = result.fullName.value,
            phone = result.phone
        )
    }
}