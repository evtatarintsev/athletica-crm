package ru.athletica.crm.modules.customers

import org.jetbrains.exposed.sql.insert
import org.junit.jupiter.api.assertThrows
import ru.athletica.crm.db.runDbTest
import kotlin.test.Test
import kotlin.test.assertEquals


class CustomersTest {

    @Test
    fun FindCustomerById_ShouldReturnCustomer_WhenItExists() = runDbTest {
        // Arrange
        val expected = Customer(CustomerId(), "Ivan".toCustomerName())
        CustomersSqlTable.insert {
            it[id] = expected.id.value
            it[fullName] = expected.fullName.value
        }

        // Act
        val actual = Customers().byId(expected.id)

        // Assert
        assertEquals(expected.id, actual.id)
    }

    @Test
    fun FindCustomerById_ShouldThrowException_WhenCustomerDoesNotExist() = runDbTest {
        // Act
        assertThrows<NoSuchElementException> {
            Customers().byId(CustomerId())
        }
    }
}
