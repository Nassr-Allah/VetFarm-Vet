package com.nassrallah.vetfarmvet.feature_auth.domain.use_case

import com.nassrallah.vetfarmvet.feature_auth.domain.repository.AuthRepository
import com.nassrallah.vetfarmvet.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearVetDataUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            authRepository.clearUserData()
            emit(Resource.Success("Data Cleared"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}