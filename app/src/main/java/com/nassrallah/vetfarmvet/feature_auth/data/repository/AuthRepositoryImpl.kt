package com.nassrallah.vetfarmvet.feature_auth.data.repository

import com.nassrallah.vetfarmvet.feature_auth.data.data_source.AuthDataSource
import com.nassrallah.vetfarmvet.feature_auth.data.data_source.local.AppDataStore
import com.nassrallah.vetfarmvet.feature_auth.data.dto.LoginCredentialsDTO
import com.nassrallah.vetfarmvet.feature_auth.data.dto.LoginResponseDTO
import com.nassrallah.vetfarmvet.feature_auth.data.dto.VetDTO
import com.nassrallah.vetfarmvet.feature_auth.domain.repository.AuthRepository
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val appDataStore: AppDataStore
) : AuthRepository {

    override suspend fun loginVet(loginCredentials: LoginCredentialsDTO): LoginResponseDTO {
        try {
            val response = authDataSource.loginVet(loginCredentials)
            if (response.status == HttpStatusCode.Accepted) {
                val loginResponse = response.body<LoginResponseDTO>()
                return loginResponse
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                throw Exception(message)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createVet(vet: VetDTO): String {
        try {
            val response = authDataSource.createVet(vet)
            if (response.status == HttpStatusCode.Created) {
                val successMessage = response.body<String>()
                return successMessage
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                throw Exception(message)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun retrieveVetToken(): Flow<String> = appDataStore.token

    override suspend fun saveVetToken(token: String) {
        appDataStore.saveToken(token)
    }

    override suspend fun retrieveVetId(): Flow<Int> = appDataStore.id

    override suspend fun saveVetId(id: Int) {
        appDataStore.saveId(id)
    }

    override suspend fun clearUserData() {
        appDataStore.clearData()
    }

}