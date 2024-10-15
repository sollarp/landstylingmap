package com.ldstack.stylinggooglemap.data.dto


import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


fun List<Site2>.toDataModel(): List<DataModel> {

    return GeometryConverter.parseData(this)
}

object GeometryConverter {
    fun parseData(sites: List<Site2>): List<DataModel> {

        val listOfDataModel = mutableListOf<DataModel>()
        for (site in sites) {

            val id = site.id
            val name = site.name
            val geometryString = site.geometry
            val siteCategory = site.siteCategory
            val type =
                Json.parseToJsonElement(geometryString).jsonObject["type"]?.jsonPrimitive?.content
            val geometryElement = Json.parseToJsonElement(geometryString).jsonObject
            val coordinatesElement =
                geometryElement["coordinates"]?.jsonArray ?: error("Missing coordinates")
            println("size of polygons begining  = ${coordinatesElement.size}")
            if (type == "MultiPolygon") {
                for (polygon in coordinatesElement) {
                    val convertedToDoubles = convertJsonElementToListOfLists(polygon.jsonArray)

                    listOfDataModel.add(
                        DataModel(
                            id = id,
                            name = name,
                            polygonPoints = convertedToDoubles,
                            siteCategory = siteCategory
                        )
                    )

                }
            } else {
                for (polygon in coordinatesElement) {
                    val convertedToDoubles = convertJsonElementToList(polygon.jsonArray)
                    listOfDataModel.add(
                        DataModel(
                            id = id,
                            name = name,
                            polygonPoints = convertedToDoubles,
                            siteCategory = siteCategory
                        )
                    )
                }
            }
        }
        return listOfDataModel
    }
}

fun convertJsonElementToListOfLists(jsonElement: JsonElement): List<List<Double>> {
    val coordinatesList = mutableListOf<List<Double>>()

    // Ensure the JsonElement is a JsonArray
    if (jsonElement is JsonArray) {
        // Traverse the JsonArray structure
        for (polygon in jsonElement) {  // Outer array (MultiPolygon)
            if (polygon is JsonArray) {
                for (ring in polygon) {  // Middle array (Polygons)
                    if (ring is JsonArray) {
                        // Convert each innermost array (longitude-latitude pair) to a List<Double>
                        val coordinates = ring.map { it.jsonPrimitive.double }
                        coordinatesList.add(coordinates)
                    }
                }
            }
        }
    }

    return coordinatesList


}

fun convertJsonElementToList(jsonElement: JsonElement): List<List<Double>> {
    val coordinatesList = mutableListOf<List<Double>>()

    // Ensure the JsonElement is a JsonArray
    if (jsonElement is JsonArray) {
        for (polygon in jsonElement) {
            if (polygon is JsonArray) {
                // Traverse the JsonArray structure
                val coordinates = polygon.map { it.jsonPrimitive.double }
                coordinatesList.add(coordinates)
            }
        }
    }
    return coordinatesList
}

