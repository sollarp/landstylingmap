package com.ldstack.stylinggooglemap.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SitesByDistrict(
    @SerialName("data")
    val `data`: List<DataX>,
    @SerialName("paginatorInfo")
    val paginatorInfo: PaginatorInfo
)