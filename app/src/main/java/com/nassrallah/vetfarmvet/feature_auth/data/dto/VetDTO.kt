package com.nassrallah.vetfarmvet.feature_auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class VetDTO(
    val id: Int? = null,
    val user: UserDTO,
    val agreementNumber: String,
    val avnNumber: String
)
