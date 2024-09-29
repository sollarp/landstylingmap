package com.ldstack.stylinggooglemap.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("sitesByDistrict")
    val sitesByDistrict: SitesByDistrict
)