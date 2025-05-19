package com.nassrallah.vetfarmvet.feature_auth.presentation.professional_info_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfessionalInfoViewModel @Inject constructor() : ViewModel() {

    private val _form = MutableStateFlow(ProfessionalInfoFormState())
    val form: StateFlow<ProfessionalInfoFormState> = _form

    fun updateAgreementNumber(value: String) {
        _form.value = _form.value.copy(
            agreementNumber = value
        )
    }

    fun updateAvnNumber(value: String) {
        _form.value = _form.value.copy(
            avnNumber = value
        )
    }

    fun validateInput(): Boolean {
        return _form.value.agreementNumber.isNotBlank() &&
                _form.value.avnNumber.isNotBlank()
    }

}