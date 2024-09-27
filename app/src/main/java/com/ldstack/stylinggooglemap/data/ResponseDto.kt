package com.ldstack.stylinggooglemap.data

import kotlinx.serialization.Serializable


@Serializable
data class ResponseDto<T>(
    val data: T
)

@Serializable
data class SiteCategory(
    val id: Int,
    val color: String?,
    val stroke_color: String?,
    val stroke_opacity: Double?,
    val stroke_weight: Int?
)

@Serializable
data class Site(
    val id: Int,
    val geometry: String?,
    val name: String?,
    val siteCategory: SiteCategory?
)

@Serializable
data class PaginatorInfo(
    val currentPage: Int,
    val hasMorePages: Boolean,
    val total: Int,
    val lastPage: Int
)

@Serializable
data class SitesByDistrictData(
    val data: List<Site>,
    val paginatorInfo: PaginatorInfo
)

@Serializable
data class SitesByDistrictResponse(
    val sitesByDistrict: SitesByDistrictData
)


