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
        //val getwer = "{\"type\":\"Polygon\",\"coordinates\":[[[-2.958706932,51.317009834],[-2.95813063,51.317002225],[-2.957749135,51.317063103],[-2.957371698,51.317118907],[-2.956726403,51.317167102],[-2.956608708,51.316984469],[-2.956344908,51.3168272],[-2.955683379,51.316761249],[-2.955590034,51.316619199],[-2.955679321,51.315523373],[-2.955764548,51.31549547],[-2.956466662,51.31558679],[-2.957083547,51.315690793],[-2.957529978,51.315804942],[-2.958073812,51.31603324],[-2.958443132,51.316182901],[-2.958719107,51.316271683],[-2.958678522,51.316365538],[-2.958654172,51.316649638],[-2.958706932,51.317009834]]]}"
        println("geo: $geo")
        val geoToJson = Json.decodeFromString<Geometry>(geo)
        val falt = geoToJson.coordinates.map { it }[0]
        return falt
    }
}

/*object GeometryConverter {
    fun parseData(geo: String): List<Double> {
        val geoToJson = Json.decodeFromString<Geometry>(geo)
        println("geoToJson: $geoToJson")
        val coordinates = geoToJson.coordinates[0]
        println("cordinates: $coordinates")
        return coordinates
    }
}*/

