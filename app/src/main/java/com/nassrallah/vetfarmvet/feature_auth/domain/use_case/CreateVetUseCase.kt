package com.nassrallah.vetfarmvet.feature_auth.domain.use_case

import com.nassrallah.vetfarmvet.feature_auth.data.dto.TransporterDTO
import com.nassrallah.vetfarmvet.feature_auth.data.dto.VetDTO
import com.nassrallah.vetfarmvet.feature_auth.domain.repository.AuthRepository
import com.nassrallah.vetfarmvet.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateVetUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(vet: VetDTO): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val successMessage = authRepository.createVet(vet)
            emit(Resource.Success(successMessage))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}