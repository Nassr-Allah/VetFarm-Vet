package com.nassrallah.vetfarmvet.feature_auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDTO(
    val token: String,
    val id: Int
)
