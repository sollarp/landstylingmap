package com.ldstack.stylinggooglemap.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    @SerialName("type")
    val type: String,
    @SerialName("coordinates")
    val coordinates: List<List<List<Double>>>
)


@Serializable
data class SiteCategory(
    @SerializedName("id")
    val id: Int,
    @SerializedName("color")
    val color: String,
    @SerializedName("stroke_color")
    val strokeColor: String,
    @SerializedName("stroke_opacity")
    val strokeOpacity: Double,
    @SerializedName("stroke_weight")
    val strokeWeight: Int
)