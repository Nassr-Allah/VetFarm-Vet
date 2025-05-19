package com.nassrallah.vetfarmvet.feature_requests.data.dto

import com.nassrallah.vetfarmvet.feature_requests.domain.entity.Category
import kotlinx.serialization.Serializable

@Serializable
data class SellerDTO(
    val id: Int? = null,
    val user: UserDTO,
    val businessName: String,
    val wilaya: String,
    val commune: String,
    val address: String,
    val category: Category,
    val products: List<ProductDTO> = emptyList()
)