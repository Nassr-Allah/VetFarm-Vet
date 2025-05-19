package com.nassrallah.vetfarmvet.feature_requests.domain.repository

import com.nassrallah.vetfarmvet.feature_requests.data.dto.VetRequestDTO
import com.nassrallah.vetfarmvet.util.Resource
import kotlinx.coroutines.flow.Flow

interface VetRequestRepository {

    suspend fun getAllVetRequests(token: String): Flow<Resource<List<VetRequestDTO>>>

    suspend fun getVetRequestById(id: Int, token: String): Flow<Resource<VetRequestDTO>>

}