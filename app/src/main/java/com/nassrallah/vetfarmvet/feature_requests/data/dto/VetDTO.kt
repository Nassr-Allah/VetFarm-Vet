package com.nassrallah.vetfarmvet.feature_requests.data.dto

import com.nassrallah.vetfarmvet.feature_requests.data.dto.UserDTO
import kotlinx.serialization.Serializable

@Serializable
data class VetDTO(
    val id: Int? = null,
    val user: UserDTO,
    val agreementNumber: String,
    val avnNumber: String
)
