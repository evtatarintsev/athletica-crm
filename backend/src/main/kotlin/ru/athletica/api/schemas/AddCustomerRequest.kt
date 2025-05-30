package ru.athletica.api.schemas

import kotlinx.serialization.Serializable

@Serializable
data class AddCustomerRequest(
    val fullName: String,
    val phone: String? = null
)