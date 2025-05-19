package com.nassrallah.vetfarmvet.feature_requests.data.data_source.remote

import com.nassrallah.vetfarmvet.util.Constants.Companion.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class VetRequestDataSource @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getAllRequests(token: String): HttpResponse {
        return client.get("$BASE_URL/vet-request") {
            bearerAuth(token)
        }
    }

    suspend fun getVetRequestById(id: Int, token: String): HttpResponse {
        return client.get("$BASE_URL/vet-request/$id") {
            bearerAuth(token)
        }
    }

}