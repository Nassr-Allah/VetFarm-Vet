package com.nassrallah.vetfarmvet.feature_auth.presentation.professional_info_screen

import com.nassrallah.vetfarmvet.feature_auth.data.dto.CommuneDTO
import com.nassrallah.vetfarmvet.feature_auth.data.dto.WilayaDTO
import com.nassrallah.vetfarmvet.feature_auth.domain.entity.Category

data class ProfessionalInfoScreenState(
    val isLoading: Boolean = false,
    val error: String? = null
)
