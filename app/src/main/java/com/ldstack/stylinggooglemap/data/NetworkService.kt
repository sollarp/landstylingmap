package com.ldstack.stylinggooglemap.data

import com.google.gson.GsonBuilder
import com.ldstack.stylinggooglemap.data.dto.Data
import com.ldstack.stylinggooglemap.data.dto.ResponseModule
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

    suspend fun getCountries(): Data {
        val response = ktorClient.post(URL) {
            contentType(ContentType.Application.Json)
            setBody(query)
        }
        val responseBody = response.body<String>()

        val gson = GsonBuilder().setPrettyPrinting().create()
        val root = gson.fromJson(responseBody, ResponseModule::class.java)
        return root.data
    }
}

val query =
        "{\"query\":\"query{sitesByDistrict(district_id: 13, first: 38, page: 1, filter: " +
        "{ site_categories: { map_type_category_id: [13, 16] } }){data{id geometry name " +
        "siteCategory{id color stroke_color stroke_opacity stroke_weight}} " +
        "paginatorInfo{currentPage hasMorePages total lastPage}}}\",\"variables\":{}}"

