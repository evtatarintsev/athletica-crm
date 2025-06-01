package ru.athletica.crm.api.schemas


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class AddCustomerResponse(
    @Contextual @SerialName(value = "id")
    val id: UUID,
    @SerialName(value = "fullName")
    val fullName: String,
    @SerialName(value = "phone")
    val phone: String? = null

)
