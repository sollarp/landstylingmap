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

