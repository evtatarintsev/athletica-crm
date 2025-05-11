package ru.athletica.crm.api

import kotlinx.datetime.LocalDate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import ru.athletica.api.schemas.CustomerInList
import java.util.UUID

@RestController
@RequestMapping("/customer")
class CustomersList {

    @GetMapping
    fun getCustomers(): List<CustomerInList> {
        return listOf(CustomerInList(id = UUID.randomUUID(), fullName = "ivan", birthday = LocalDate.parse("2022-01-01")))
    }
}