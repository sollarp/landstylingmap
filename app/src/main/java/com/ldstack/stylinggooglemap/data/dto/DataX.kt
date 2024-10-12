package com.ldstack.stylinggooglemap.data.dto


import com.google.gson.JsonPrimitive
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.double
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.json.JSONArray


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

/*val jsonElement = Json.parseToJsonElement(jsonString).jsonObject
val type = jsonElement["type"]?.jsonPrimitive?.content
val id = jsonElement["id"]?.jsonPrimitive?.int ?: error("Missing id")
val name = jsonElement["name"]?.jsonPrimitive?.content ?: error("Missing name")
val geometryString = jsonElement["geometry"]?.jsonPrimitive?.content ?: error("Missing geometry")
val geometryElement = Json.parseToJsonElement(geometryString).jsonObject
val coordinatesElement = geometryElement["coordinates"]?.jsonArray ?: error("Missing coordinates")
val siteCategory = SiteCategory2(
    id = jsonElement["siteCategory"]?.jsonObject?.get("id")?.jsonPrimitive?.int ?: error("Missing siteCategory id"),
    color = jsonElement["siteCategory"]?.jsonObject?.get("color")?.jsonPrimitive?.content ?: error("Missing siteCategory color"),
    stroke_color = jsonElement["siteCategory"]?.jsonObject?.get("stroke_color")?.jsonPrimitive?.content ?: error("Missing siteCategory stroke_color"),
    stroke_opacity = jsonElement["siteCategory"]?.jsonObject?.get("stroke_opacity")?.jsonPrimitive?.double ?: error("Missing siteCategory stroke_opacity"),
    stroke_weight = jsonElement["siteCategory"]?.jsonObject?.get("stroke_weight")?.jsonPrimitive?.int ?: error("Missing siteCategory stroke_weight")
)*/
/*
        println("id print: $id, name: $name")
        return when (type) {
               *//*"Polygon" -> {
                   // Assuming PolygonType is your data class for Polygon type
                   val polygonData = Json.decodeFromString<PolygonType>(jsonString) // Handle Polygon case
                   polygonData.coordinates[0] // Return the first set of coordinates
               }*//*
            "MultiPolygon" -> {

                val geometryDataList = mutableListOf<DataModel>()
                for (polygon in coordinatesElement) {
                    if (polygon is JsonArray) {
                        // Each polygon can have multiple rings (outer boundary + holes)
                        for (ring in polygon) {
                            if (ring is JsonArray) {
                                // Collect all points (longitude and latitude pairs) into a flat list of doubles
                                val coordinates = mutableListOf<Double>()
                                for (point in ring) {
                                    if (point is JsonArray) {
                                        point.forEach { coordinates.add(it.jsonPrimitive.double) }
                                    }
                                }

                                // Create a GeometryData object for each ring and add it to the list
                                geometryDataList.add(
                                    DataModel(
                                        id = id,
                                        name = name,
                                        polygonPoints = coordinates,
                                        siteCategory = siteCategory
                                    )
                                )
                            }
                        }
                    }
                }

                return geometryDataList
                *//*val multiPolygonData = Json.decodeFromString<MultiPolygonType>(jsonString) // Handle MultiPolygon case
                multiPolygonData.coordinates[0].map { it[0] } // Return the first set of coordinates*//*
            }
            // Return null or handle other cases
            else -> { mutableListOf()}
        }
    }
}*/

/*
fun List<Site2>.toDataModel(): List<List<DataModel>> {
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
    fun parseData(jsonString: String): List<List<List<Double>>>  {
        val jsonElement = Json.parseToJsonElement(jsonString)
        val jsonObject = jsonElement.jsonObject

        val type = jsonObject["type"]?.jsonPrimitive?.content

        return when (type) {
            */
/*"Polygon" -> {
                // Assuming PolygonType is your data class for Polygon type
                val polygonData = Json.decodeFromString<PolygonType>(jsonString) // Handle Polygon case
                polygonData.coordinates[0] // Return the first set of coordinates
            }*//*

            "MultiPolygon" -> {
                val multiPolygonData = Json.decodeFromString<MultiPolygonType>(jsonString) // Handle MultiPolygon case
                println("multiPolygonData: ${multiPolygonData.coordinates}")
                multiPolygonData.coordinates.first() // Return the first set of coordinates
            }
             // Return null or handle other cases
            else -> { emptyList() }
        }
    }
}*/
