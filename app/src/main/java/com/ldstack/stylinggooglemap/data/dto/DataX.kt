package com.ldstack.stylinggooglemap.data.dto


import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


fun List<Site2>.toDataModel(): List<DataModel> {
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
    fun parseData(jsonString: String): List<List<Double>>  {
        val jsonElement = Json.parseToJsonElement(jsonString)
        val jsonObject = jsonElement.jsonObject

        val type = jsonObject["type"]?.jsonPrimitive?.content

        return when (type) {
            "Polygon" -> {
                // Assuming PolygonType is your data class for Polygon type
                val polygonData = Json.decodeFromString<PolygonType>(jsonString) // Handle Polygon case
                polygonData.coordinates[0] // Return the first set of coordinates
            }
            "MultiPolygon" -> {
                val multiPolygonData = Json.decodeFromString<MultiPolygonType>(jsonString) // Handle MultiPolygon case
                println("multiPolygonData: ${multiPolygonData.coordinates}")
                multiPolygonData.coordinates[0].map { it[0] } // Return the first set of coordinates
            }
             // Return null or handle other cases
            else -> { emptyList() }
        }
    }
}