package ru.athletica.crm.api.schemas


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddCustomerRequest(
    @SerialName(value = "fullName")
    val fullName: String,

    @SerialName(value = "phone")
    val phone: String? = null

)
