package com.ldstack.stylinggooglemap.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginatorInfo(
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("hasMorePages")
    val hasMorePages: Boolean,
    @SerialName("lastPage")
    val lastPage: Int,
    @SerialName("total")
    val total: Int
)