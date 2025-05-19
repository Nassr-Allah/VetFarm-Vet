package com.nassrallah.vetfarmvet.feature_requests.presentation.requests_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassrallah.vetfarmvet.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmvet.feature_requests.data.dto.VetRequestDTO
import com.nassrallah.vetfarmvet.feature_requests.domain.repository.VetRequestRepository
import com.nassrallah.vetfarmvet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestsScreenViewModel @Inject constructor(
    private val requestRepository: VetRequestRepository,
    private val retrieveTokenUseCase: RetrieveTokenUseCase
) : ViewModel() {

    private val _requestsScreenState = MutableStateFlow(RequestsScreenState())
    val requestsScreenState: StateFlow<RequestsScreenState> = _requestsScreenState

    val vetRequestsList = mutableStateOf(listOf<VetRequestDTO>())

    init {
        getToken()
    }

    fun getVetRequests() {
        viewModelScope.launch {
            val token = _requestsScreenState.value.token ?: return@launch
            requestRepository.getAllVetRequests(token).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("RequestsScreenViewModel", "Success: ${result.data}")
                        _requestsScreenState.value = _requestsScreenState.value.copy(
                            data = result.data ?: emptyList(),
                            isLoading = false
                        )
                        vetRequestsList.value = result.data ?: emptyList()
                    }

                    is Resource.Loading -> {
                        Log.d("RequestsScreenViewModel", "Loading...")
                        _requestsScreenState.value = _requestsScreenState.value.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Error -> {
                        Log.d("RequestsScreenViewModel", "Error: ${result.message}")
                        _requestsScreenState.value = _requestsScreenState.value.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getToken() {
        viewModelScope.launch {
            retrieveTokenUseCase().collect {
                _requestsScreenState.value = _requestsScreenState.value.copy(
                    token = it
                )
            }
        }
    }

}