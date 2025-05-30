package ru.athletica.api.schemas

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class AddCustomerResponse(
    @Contextual
    val id: UUID,
    val fullName: String,
    val phone: String? = null
)
