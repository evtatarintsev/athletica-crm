package ru.athletica.crm.modules.customers

import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.sql.selectAll
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import ru.athletica.crm.db.suspendTransaction
import kotlin.test.Test
import kotlin.test.assertEquals

@Testcontainers
@SpringBootTest
class CustomerTest {
    companion object {
        @Container
        @JvmStatic
        val db = PostgreSQLContainer("postgres:17.4-alpine")

        @DynamicPropertySource
        @JvmStatic
        fun registerDBContainer(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", db::getJdbcUrl)
            registry.add("spring.datasource.username", db::getUsername)
            registry.add("spring.datasource.password", db::getPassword)
        }
    }

    @Test
    fun `Сохранение клиента работает1`() = runTest {
        suspendTransaction {
            // Act
            val customer = Customer(CustomerId(), "Ivan".toCustomerName())

            // Act
            customer.save()

            // Assert
            val record = CustomersSqlTable
                .select(CustomersSqlTable.fullName)
                .where { CustomersSqlTable.id.eq(customer.id.value) }
                .first()

            assertEquals(record[CustomersSqlTable.fullName], customer.fullName.value)
        }
    }
}
