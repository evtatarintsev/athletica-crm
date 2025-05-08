package ru.athletica.crm.modules.customers

import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.sql.insert
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
class CustomersTest {
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
    fun `Поиск клиента по id`() = runTest {
        // Arrange
        val expected = Customer(CustomerId(), "Ivan".toCustomerName())
        suspendTransaction {
            CustomersSqlTable.insert {
                it[id] = expected.id.value
                it[fullName] = expected.fullName.value
            }
        }

        // Act
        val actual = Customers().byId(expected.id)

        // Assert
        assertEquals(expected.id, actual.id)
    }
}
