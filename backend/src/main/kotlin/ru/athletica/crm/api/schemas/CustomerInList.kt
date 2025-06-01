package ru.athletica.crm.api.schemas


import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Contextual
import java.util.UUID


@Serializable
data class CustomerInList (

    @Contextual @SerialName(value = "id")
    val id: UUID,

    @SerialName(value = "fullName")
    val fullName: String,

    @SerialName(value = "phone")
    val phone: String? = null,

    @Contextual @SerialName(value = "birthday")
    val birthday: LocalDate? = null

)
