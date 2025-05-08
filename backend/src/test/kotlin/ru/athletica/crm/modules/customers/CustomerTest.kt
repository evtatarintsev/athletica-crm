package ru.athletica.crm.modules.customers

import kotlinx.coroutines.runBlocking
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class CustomerTest {
    @Test
    fun `Сохранение клиента работает`() = runBlocking {
        Customer(CustomerId(), "Ivan".toCustomerName()).save()
    }
}