package com.ldstack.stylinggooglemap.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataModel(
    @SerialName("polygonPoints")
    val polygonPoints: List<List<Double>>,
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("siteCategory")
    val siteCategory: SiteCategory
)

@Serializable
data class PolygonPoints(
    @SerialName("latAndLng")
    val points: List<Double>
)


