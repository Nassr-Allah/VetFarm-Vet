package com.nassrallah.vetfarmvet.feature_auth.domain.use_case

import com.nassrallah.vetfarmvet.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(): Flow<String> = repository.retrieveVetToken()

}