package ru.athletica.crm.modules.customers

import org.jetbrains.exposed.sql.Table


/**
 * Таблица для хранения клиентов.
 */
object CustomersSqlTable : Table("customers") {
    val id = uuid("id")
    val fullName = varchar("full_name", length = 50)
    val phone = varchar("phone", length = 20).nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Customers_ID")
}
