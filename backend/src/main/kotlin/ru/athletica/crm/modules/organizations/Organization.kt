package ru.athletica.crm.modules.organizations

import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.insert

class Organization(val id: OrganizationId, val name: OrganizationName) {
    /**
     * Сохраняет организацию в БД.
     */
    context(_: Transaction)
    fun save() {
        OrganizationsSqlTable.insert {
            it[id] = this@Organization.id.value
            it[name] = this@Organization.name.value
        }
    }
}