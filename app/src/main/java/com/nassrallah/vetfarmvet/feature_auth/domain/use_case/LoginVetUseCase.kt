package com.nassrallah.vetfarmvet.feature_auth.domain.use_case

import com.nassrallah.vetfarmvet.feature_auth.data.dto.LoginCredentialsDTO
import com.nassrallah.vetfarmvet.feature_auth.data.dto.LoginResponseDTO
import com.nassrallah.vetfarmvet.feature_auth.domain.repository.AuthRepository
import com.nassrallah.vetfarmvet.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginVetUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(loginCredentials: LoginCredentialsDTO): Flow<Resource<LoginResponseDTO>> = flow {
        try {
            emit(Resource.Loading())
            val response = authRepository.loginVet(loginCredentials)
            authRepository.saveVetToken(response.token)
            authRepository.saveVetId(response.id)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}