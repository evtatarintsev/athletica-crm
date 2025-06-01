package ru.athletica.crm.api.schemas


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserSetting(

    @SerialName(value = "key")
    val key: String,

    @Contextual @SerialName(value = "value")
    val `value`: Any

)

