package ru.athletica.crm.api.customers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.athletica.api.schemas.CustomerDetails
import ru.athletica.crm.db.suspendTransaction
import ru.athletica.crm.modules.customers.Customer
import ru.athletica.crm.modules.customers.CustomerId
import ru.athletica.crm.modules.customers.Customers
import java.util.UUID

@RestController
@RequestMapping("/api/customers")
class CustomerDetailsController(private val customers: Customers) {

    @GetMapping("/{id}")
    suspend fun getCustomerDetails(@PathVariable id: UUID): CustomerDetails = suspendTransaction {
        val customer = customers.byId(CustomerId(id))
        return@suspendTransaction customer.toCustomerDetails()
    }
}

fun Customer.toCustomerDetails() = CustomerDetails(
    id = id.value,
    fullName = fullName.value,
    phone = phone,
    // Since we don't have these fields in the Customer model, we'll set them to null
    birthday = null,
    email = null,
    address = null,
    notes = null
)