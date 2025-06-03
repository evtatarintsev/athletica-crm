package ru.athletica.crm.modules.organizations

import java.util.UUID

/**
 * Id организации.
 */
@JvmInline
value class OrganizationId(val value: UUID){
    /**
     * Новый случайный id организации.
     */
    constructor() : this(UUID.randomUUID())
}

/**
 * Название организации.
 */
@JvmInline
value class OrganizationName(val value: String)

fun String.toOrganizationName() = OrganizationName(this)