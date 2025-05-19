package com.nassrallah.vetfarmvet.feature_auth.domain.repository

import com.nassrallah.vetfarmvet.feature_auth.data.dto.LoginCredentialsDTO
import com.nassrallah.vetfarmvet.feature_auth.data.dto.LoginResponseDTO
import com.nassrallah.vetfarmvet.feature_auth.data.dto.VetDTO
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun loginVet(loginCredentials: LoginCredentialsDTO): LoginResponseDTO

    suspend fun createVet(vet: VetDTO): String

    suspend fun retrieveVetToken(): Flow<String>

    suspend fun saveVetToken(token: String)

    suspend fun retrieveVetId(): Flow<Int>

    suspend fun saveVetId(id: Int)

    suspend fun clearUserData()

}