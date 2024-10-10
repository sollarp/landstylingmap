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
    "{\"query\":\"query{sitesByDistrict(district_id: 13, first: 39, page: 1, filter: " +
            "{ site_categories: { map_type_category_id: [13, 16] } }){data{id geometry name " +
            "siteCategory{id color stroke_color stroke_opacity stroke_weight}} " +
            "paginatorInfo{currentPage hasMorePages total lastPage}}}\",\"variables\":{}}"

val bearerToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9." +
        "eyJhdWQiOiI0IiwianRpIjoiNDk5MjNhNDQ5MzA1ZjM5MzI4NmNl" +
        "NTAzN2M2OTg2ZDllY2YwYjhkNDU0NGJiMzFhYzI2MWMxNTI3ZTY0MWY" +
        "2ZTM1NDRjNWU1NTYwZjYyOTAiLCJpYXQiOjE3Mjc0NDQ3NjcuMTcxNTksIm5i" +
        "ZiI6MTcyNzQ0NDc2Ny4xNzE1OTMsImV4cCI6MTc1ODk4MDc2Ny4xNjQwNTUs" +
        "InN1YiI6IjUzMjMiLCJzY29wZXMiOltdfQ.I8agO7N_hRMFHHE7zHRXaTE0_" +
        "7mg4dLG3HyV7Soh9fwbtY8pEkOzvl0_Vx61C3y4y-tspdok6-BcQ_3CfI75lUr2" +
        "RjhgJ2OZmk2n7i8oOdALx26yr68ZlPghZr6gl588kstpRjzV1xm2pz76ChiC4bY0" +
        "5bRgfwSv3lwgQlYgFOh2EoSN1rQXQF4_uJ22lRwKGHmZAgv8017dGb8IkGa3Awevu" +
        "J6BUPVYjB1WjlIZS6cqD7UXlKkkR4675JmNpI4_CpxeDHoaFYK8RTmhdO5rWVC03T85" +
        "d8FTIj7LgFIQcNfnRKPWEn8Xz78aEWjgMogYm7V3zEe8ESZ6dvsCWHoFahtAOa-i5oDX4T" +
        "ubWyTRdb3AJvrR_-jhSMuucvH5ElUkw0V8bKHAMrkdlGoShCNyS45wBTccEQLn5Zs0uWLh" +
        "v89nEEHtKoPZY8vjxVlQqLiWGKO-ykoJBJaH42BcQiP_SwpFFcopM1zMHVe2nMzpgWGd7RJ" +
        "IHKs2wU1JYs3PUnQMeKuqUI5A2DNPASBONswB-UYoDciRxMTge162El_mQ8g9TvcOkAm5Zisr" +
        "diaUFClWwiWLE4yAkHVzIiqP_z2Yhi73CGfdIbz2y7LQvNfLj5WzzhjjuiZZbScNxLFZBYsMIfdr" +
        "V9XWGkWDD48_KqSD8PKjSG26kjlLy0_fvswMYis"

