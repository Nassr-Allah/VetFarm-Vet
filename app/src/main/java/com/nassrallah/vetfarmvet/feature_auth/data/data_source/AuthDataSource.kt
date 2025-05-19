package com.nassrallah.vetfarmvet.feature_auth.data.data_source

import com.nassrallah.vetfarmvet.feature_auth.data.dto.LoginCredentialsDTO
import com.nassrallah.vetfarmvet.feature_auth.data.dto.TransporterDTO
import com.nassrallah.vetfarmvet.feature_auth.data.dto.VetDTO
import com.nassrallah.vetfarmvet.util.Constants.Companion.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun loginVet(loginCredentials: LoginCredentialsDTO): HttpResponse {
        return httpClient.post("$BASE_URL/auth/login/vet") {
            setBody(loginCredentials)
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun createVet(vet: VetDTO): HttpResponse {
        return httpClient.post("$BASE_URL/register/vet") {
            setBody(vet)
            contentType(ContentType.Application.Json)
        }
    }

}