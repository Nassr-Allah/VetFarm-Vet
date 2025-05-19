package com.nassrallah.vetfarmvet.feature_auth.presentation.signup_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nassrallah.vetfarmvet.R
import com.nassrallah.vetfarmvet.feature_auth.data.dto.UserDTO
import com.nassrallah.vetfarmvet.feature_auth.presentation.otp_screen.OtpScreen
import com.nassrallah.vetfarmvet.feature_auth.presentation.professional_info_screen.ProfessionalInfoScreen
import com.nassrallah.vetfarmvet.ui.components.CustomButton
import com.nassrallah.vetfarmvet.ui.components.CustomTextField
import com.nassrallah.vetfarmvet.ui.components.ScreenTitle
import com.nassrallah.vetfarmvet.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmvet.ui.theme.Gray

class SignupScreen : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<SignupScreenViewModel>()
        val form = screenViewModel.form.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundWhite) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    ScreenTitle(title = "معلومات العمل") {
                        navigator.pop()
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    SignupFormSection(formState = form.value, viewModel = screenViewModel)
                }
                CustomButton(text = "التالي", icon = R.drawable.ic_arrow_right) {
                    if (screenViewModel.validateInput()) {
                        val user = UserDTO(
                            firstName = form.value.firstName,
                            lastName = form.value.lastName,
                            email = form.value.email,
                            phoneNumber = ""
                        )
                        navigator.push(ProfessionalInfoScreen(user))
                    }
                }
            }
        }
    }

    @Composable
    fun SignupFormSection(formState: SignupFormState, viewModel: SignupScreenViewModel) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "قم بانشاء حساب", style = MaterialTheme.typography.bodyLarge, color = Gray)
            CustomTextField(value = formState.firstName, onValueChange = { viewModel.updateFirstName(it) }, label = "الاسم")
            CustomTextField(value = formState.lastName, onValueChange = { viewModel.updateLastName(it) }, label = "اللقب")
            CustomTextField(value = formState.email, onValueChange = { viewModel.updateEmail(it) }, label = "البريد الالكتروني")
            CustomTextField(value = formState.age, onValueChange = { viewModel.updateAge(it) }, label = "العمر")
        }
    }
}