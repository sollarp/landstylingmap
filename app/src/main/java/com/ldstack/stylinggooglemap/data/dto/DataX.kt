package com.ldstack.stylinggooglemap.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class DataX(
    @SerialName("geometry")
    val geometry: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("siteCategory")
    val siteCategory: SiteCategory
)

fun List<DataX>.toDataModel(): List<DataModel> {
    return this.map {
        DataModel(
            polygonPoints = GeometryConverter.parseData(it.geometry),
            id = it.id,
            name = it.name,
            siteCategory = it.siteCategory
        )
    }
}


object GeometryConverter {
    fun parseData(geo: String): List<List<Double>> {
        println("geo: $geo")
        val geoToJson = Json.decodeFromString<Geometry>(geo)
        val falt = geoToJson.coordinates.map { it }[0]
        return falt
    }
}

