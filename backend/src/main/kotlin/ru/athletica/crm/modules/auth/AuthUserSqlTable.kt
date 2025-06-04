package ru.athletica.crm.modules.auth



import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ru.athletica.crm.modules.organizations.OrganizationsSqlTable

/**
 * Таблица для хранения клиентов.
 */
object AuthUsersSqlTable : Table("auth_users") {
    val id = uuid("id")
    val organizationId = uuid("organization_id").references(OrganizationsSqlTable.id)
    val createdAt = datetime("created_at")

    val login = varchar("login", length = 50).uniqueIndex()
    val password = varchar("password", length = 255)

    override val primaryKey = PrimaryKey(login, organizationId, name = "PK_login_organization_id")
}
