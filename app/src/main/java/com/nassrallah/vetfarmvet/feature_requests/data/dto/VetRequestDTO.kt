package com.nassrallah.vetfarmvet.feature_requests.data.dto

import com.nassrallah.vetfarmvet.feature_requests.domain.entity.RequestType
import kotlinx.serialization.Serializable

@Serializable
data class VetRequestDTO(
    val id: Int? = null,
    val type: RequestType,
    val seller: SellerDTO,
    val vet: VetDTO? = null
)
