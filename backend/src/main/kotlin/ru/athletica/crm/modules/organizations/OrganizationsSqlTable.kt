package ru.athletica.crm.modules.organizations

import org.jetbrains.exposed.sql.Table


object OrganizationsSqlTable : Table("organizations") {
    val id = uuid("id")
    val name = varchar("name", length = 50)

    override val primaryKey = PrimaryKey(id, name = "PK_Organizations_ID")
}