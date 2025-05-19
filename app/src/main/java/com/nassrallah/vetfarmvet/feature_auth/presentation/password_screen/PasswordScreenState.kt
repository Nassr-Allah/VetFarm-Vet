package com.nassrallah.vetfarmvet.feature_auth.presentation.password_screen

data class PasswordScreenState(
    val isLoading: Boolean = false,
    val isAccountCreated: Boolean = false,
    val error: String? = null
)
