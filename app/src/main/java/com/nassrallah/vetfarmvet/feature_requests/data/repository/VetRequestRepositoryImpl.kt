package com.nassrallah.vetfarmvet.feature_requests.data.repository

import com.nassrallah.vetfarmvet.util.Resource
import com.nassrallah.vetfarmvet.feature_requests.data.data_source.remote.VetRequestDataSource
import com.nassrallah.vetfarmvet.feature_requests.data.dto.VetRequestDTO
import com.nassrallah.vetfarmvet.feature_requests.domain.repository.VetRequestRepository
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VetRequestRepositoryImpl @Inject constructor(
    private val vetRequestDataSource: VetRequestDataSource
) : VetRequestRepository {

    override suspend fun getAllVetRequests(token: String): Flow<Resource<List<VetRequestDTO>>> = flow {
        emit(Resource.Loading())
        try {
            val response = vetRequestDataSource.getAllRequests(token)
            if (response.status.value == 200) {
                emit(Resource.Success(response.body<List<VetRequestDTO>>()))
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                emit(Resource.Error(message))
            }
        } catch (e: Exception) {
            val message = e.localizedMessage ?: "Unexpected Error"
            emit(Resource.Error(message))
        }
    }

    override suspend fun getVetRequestById(id: Int, token: String): Flow<Resource<VetRequestDTO>> = flow {
        emit(Resource.Loading())
        try {
            val response = vetRequestDataSource.getVetRequestById(id, token)
            if (response.status.value == 200) {
                emit(Resource.Success(response.body()))
            } else {
                val message = response.body<String>().ifEmpty { "Unexpected Error" }
                emit(Resource.Error(message))
            }
        } catch (e: Exception) {
            val message = e.localizedMessage ?: "Unexpected Error"
            emit(Resource.Error(message))
        }
    }
}