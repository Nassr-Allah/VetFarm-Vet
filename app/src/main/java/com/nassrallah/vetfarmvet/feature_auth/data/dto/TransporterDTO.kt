package com.nassrallah.vetfarmvet.feature_auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TransporterDTO(
    val id: Int? = null,
    val user: UserDTO
)
