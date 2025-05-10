package ru.athletica.crm.modules.customers

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.UUID


/**
 * Таблица для хранения клиентов.
 */
object CustomersSqlTable : Table("customers") {
    val id: Column<UUID> = uuid("id")
    val fullName: Column<String> = varchar("full_name", length = 50)

    override val primaryKey = PrimaryKey(id, name = "PK_Customers_ID")
}
