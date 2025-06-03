package ru.athletica.crm.modules.organizations

import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Component

@Component
class Organizations {
    /**
     * Получить организацию по id.
     */
    context(_: Transaction)
    suspend fun byId(id: OrganizationId) = OrganizationsSqlTable
        .selectAll()
        .where { OrganizationsSqlTable.id.eq(id.value) }
        .map {
            Organization(
                OrganizationId(it[OrganizationsSqlTable.id]),
                OrganizationName(it[OrganizationsSqlTable.name]),
            )
        }.first()
}