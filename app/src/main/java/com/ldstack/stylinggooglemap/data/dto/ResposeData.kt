package com.ldstack.stylinggooglemap.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(
    val data: Data2
)

@Serializable
data class Data2(
    val sitesByDistrict: SitesByDistrict2
)

@Serializable
data class SitesByDistrict2(
    val data: List<Site2>,
    val paginatorInfo: PaginatorInfo2
)

@Serializable
data class Site2(
    val id: Int,
    val geometry: String,
    val name: String,
    val siteCategory: SiteCategory2
)

@Serializable
data class SiteCategory2(
    val id: Int,
    val color: String,
    val stroke_color: String,
    val stroke_opacity: Double,
    val stroke_weight: Int
)

@Serializable
data class PaginatorInfo2(
    val currentPage: Int,
    val hasMorePages: Boolean,
    val total: Int,
    val lastPage: Int
)

@Serializable
data class PolygonType(
    val type: String,  // e.g. "Polygon"
    val coordinates: List<List<List<Double>>> // Nested list to represent the coordinates
)

@Serializable
data class MultiPolygonType(
    val type: String,  // e.g. "Polygon"
    val coordinates: List<List<List<List<Double>>>>
)