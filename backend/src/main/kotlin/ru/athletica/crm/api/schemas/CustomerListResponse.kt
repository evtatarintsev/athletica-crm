package ru.athletica.crm.api.schemas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerListResponse(
    /* Всего записей */
    @SerialName(value = "totalCount")
    val totalCount: Int,

    /* Есть ли еще клиенты */
    @SerialName(value = "hasMore")
    val hasMore: Boolean,

    /* Список клиентов */
    @SerialName(value = "customers")
    val customers: List<CustomerInList>

)
