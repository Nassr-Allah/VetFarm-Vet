package com.nassrallah.vetfarmvet.feature_requests.presentation.requests_screen

import com.nassrallah.vetfarmvet.feature_requests.data.dto.VetRequestDTO

data class RequestsScreenState(
    val isLoading: Boolean = false,
    val data: List<VetRequestDTO> = emptyList(),
    val token: String? = null,
    val error: String? = null
)
