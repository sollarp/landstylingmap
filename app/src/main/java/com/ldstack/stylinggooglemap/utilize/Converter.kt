package com.ldstack.stylinggooglemap.utilize

import com.google.android.gms.maps.model.LatLng

object Converter {
    fun convertToLatLng(points: List<List<Double>>): List<LatLng> {
        return points.map { LatLng(it[1], it[0]) }
    }
}