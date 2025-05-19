package com.nassrallah.vetfarmvet.feature_auth.presentation.password_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nassrallah.vetfarmvet.feature_auth.data.dto.TransporterDTO
import com.nassrallah.vetfarmvet.feature_auth.data.dto.VetDTO
import com.nassrallah.vetfarmvet.feature_auth.domain.use_case.CreateVetUseCase
import com.nassrallah.vetfarmvet.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordScreenViewModel @Inject constructor(
    private val createVetUseCase: CreateVetUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PasswordScreenState())
    val state: StateFlow<PasswordScreenState> = _state

    private val _form = MutableStateFlow(PasswordScreenForm())
    val form: StateFlow<PasswordScreenForm> = _form

    fun createVet(vet: VetDTO) {
        viewModelScope.launch {
            val vetCopy = vet.copy(
                user = vet.user.copy(
                    password = _form.value.password,
                    lat = 0.0,
                    lng = 0.0
                )
            )
            createVetUseCase(vetCopy).onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.d("PasswordScreenVM", "Loading...")
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        Log.d("PasswordScreenVM", "Success: ${result.data}")
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isAccountCreated = true
                        )
                    }
                    is Resource.Error -> {
                        Log.d("PasswordScreenVM", "Error: ${result.message}")
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun updatePassword(value: String) {
        _form.value = _form.value.copy(
            password = value
        )
    }

    fun updateConfirmPassword(value: String) {
        _form.value = _form.value.copy(
            confirmPassword = value
        )
    }

    fun validateInput(): Boolean {
        return _form.value.password.length >= 8 &&
                _form.value.password == _form.value.confirmPassword
    }

}