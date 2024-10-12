package com.ldstack.stylinggooglemap.data

import com.google.gson.Gson
import com.ldstack.stylinggooglemap.data.dto.Data2
import com.ldstack.stylinggooglemap.data.dto.ResponseData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson

val ktorClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        gson()
    }
    install(DefaultRequest) {
        header("Authorization", "Bearer $bearerToken")
    }
}

object NetworkService {

    private const val URL = "https://app.landstack.co.uk/graphql"

    suspend fun getCountries(): Data2 {
        val response = ktorClient.post(URL) {
            contentType(ContentType.Application.Json)
            setBody(query)
        }
        val responseBody = response.body<String>()

        val gson = Gson()
        //val root = gson.fromJson(responseBody, ResponseModule::class.java)
        val complexRoot = getQuery(responseBody)
        return complexRoot?.data!!
    }

    fun getQuery(responseBody: String): ResponseData? {
        val gson = Gson()
        val responseData = gson.fromJson(responseBody, ResponseData::class.java)
        println("responseData: $responseData")
        return responseData
    }
}

val query =
    "{\"query\":\"query{sitesByDistrict(district_id: 13, first: 100, page: 1, filter: " +
            "{ site_categories: { map_type_category_id: [13, 16] } }){data{id geometry name " +
            "siteCategory{id color stroke_color stroke_opacity stroke_weight}} " +
            "paginatorInfo{currentPage hasMorePages total lastPage}}}\",\"variables\":{}}"
