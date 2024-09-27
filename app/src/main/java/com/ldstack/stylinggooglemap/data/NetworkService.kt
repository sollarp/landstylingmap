package com.ldstack.stylinggooglemap.data

import android.content.res.Resources
import com.ldstack.stylinggooglemap.R
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
        header("Authorization", bearerToken)
    }
}

object NetworkService {

    private const val URL = "https://app.landstack.co.uk/graphql/"

    suspend fun getCountries(): List<Site> {
        val response = ktorClient.post(URL) {
            contentType(ContentType.Application.Json)
            setBody(query)
        }.body<ResponseDto<SitesByDistrictResponse>>()
        return response.data.sitesByDistrict.data
    }
}

val query =
        "{\"query\":\"query{sitesByDistrict(district_id: 13, first: 100, page: 1, filter: " +
        "{ site_categories: { map_type_category_id: [13, 16] } }){data{id geometry name " +
        "siteCategory{id color stroke_color stroke_opacity stroke_weight}} " +
        "paginatorInfo{currentPage hasMorePages total lastPage}}}\",\"variables\":{}}"

val bearerToken = Resources.getSystem().getString(R.string.bearer_token)

/*
val bearerToken =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiNDk5MjN\n" +
        "hNDQ5MzA1ZjM5MzI4NmNlNTAzN2M2OTg2ZDllY2YwYjhkNDU0NGJiMzFhYzI2MWMxNTI\n" +
        "3ZTY0MWY2ZTM1NDRjNWU1NTYwZjYyOTAiLCJpYXQiOjE3Mjc0NDQ3NjcuMTcxNTksIm5\n" +
        "iZiI6MTcyNzQ0NDc2Ny4xNzE1OTMsImV4cCI6MTc1ODk4MDc2Ny4xNjQwNTUsInN1YiI\n" +
        "6IjUzMjMiLCJzY29wZXMiOltdfQ.I8agO7N_hRMFHHE7zHRXaTE0_7mg4dLG3HyV7Soh\n" +
        "9fwbtY8pEkOzvl0_Vx61C3y4y-tspdok6-BcQ_3CfI75lUr2RjhgJ2OZmk2n7i8oOdAL\n" +
        "x26yr68ZlPghZr6gl588kstpRjzV1xm2pz76ChiC4bY05bRgfwSv3lwgQlYgFOh2EoSN\n" +
        "1rQXQF4_uJ22lRwKGHmZAgv8017dGb8IkGa3AwevuJ6BUPVYjB1WjlIZS6cqD7UXlKkk\n" +
        "R4675JmNpI4_CpxeDHoaFYK8RTmhdO5rWVC03T85d8FTIj7LgFIQcNfnRKPWEn8Xz78a\n" +
        "EWjgMogYm7V3zEe8ESZ6dvsCWHoFahtAOa-i5oDX4TubWyTRdb3AJvrR_-jhSMuucvH5\n" +
        "ElUkw0V8bKHAMrkdlGoShCNyS45wBTccEQLn5Zs0uWLhv89nEEHtKoPZY8vjxVlQqLiW\n" +
        "GKO-ykoJBJaH42BcQiP_SwpFFcopM1zMHVe2nMzpgWGd7RJIHKs2wU1JYs3PUnQMeKuq\n" +
        "UI5A2DNPASBONswB-UYoDciRxMTge162El_mQ8g9TvcOkAm5ZisrdiaUFClWwiWLE4yA\n" +
        "kHVzIiqP_z2Yhi73CGfdIbz2y7LQvNfLj5WzzhjjuiZZbScNxLFZBYsMIfdrV9XWGkWD\n" +
        "D48_KqSD8PKjSG26kjlLy0_fvswMYis"*/
