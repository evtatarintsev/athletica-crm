package ru.athletica.crm.modules.customers

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CustomersSqlTable : Table("customers") {
    val id: Column<Int> = integer("id")
    val fullName: Column<String> = varchar("name", length = 50)

    override val primaryKey = PrimaryKey(id, name = "PK_Customers_ID")
}
