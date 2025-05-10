package ru.athletica.crm.modules.customers

import ru.athletica.crm.db.runDbTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CustomerTest {
    @Test
    fun NewCustomer_ShoulsBeSavedInDb() = runDbTest {
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
