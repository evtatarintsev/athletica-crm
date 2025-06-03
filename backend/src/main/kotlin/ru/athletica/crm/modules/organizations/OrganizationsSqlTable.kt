package ru.athletica.crm.modules.organizations

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object OrganizationSqlTable : Table("organizations") {
    val id: Column<UUID> = uuid("id")
    val name: Column<String> = varchar("name", length = 50)

    override val primaryKey = PrimaryKey(id, name = "PK_Organizations_ID")
}