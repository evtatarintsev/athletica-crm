package ru.athletica.crm.modules.customers

class Customers {
    suspend fun byId(id: CustomerId): Customer {
        return Customer(CustomerId(), "Ivan".toCustomerName())
    }
}