package com.nassrallah.vetfarmvet.feature_auth.presentation.otp_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nassrallah.vetfarmvet.feature_auth.data.dto.TransporterDTO
import com.nassrallah.vetfarmvet.feature_auth.data.dto.UserDTO
import com.nassrallah.vetfarmvet.feature_auth.data.dto.VetDTO
import com.nassrallah.vetfarmvet.feature_auth.presentation.password_screen.PasswordScreen
import com.nassrallah.vetfarmvet.ui.components.CustomButton
import com.nassrallah.vetfarmvet.ui.components.CustomTextField
import com.nassrallah.vetfarmvet.ui.components.ScreenTitle
import com.nassrallah.vetfarmvet.ui.components.SmsDigitField
import com.nassrallah.vetfarmvet.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmvet.ui.theme.Gray

data class OtpScreen(private val vet: VetDTO) : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<OtpScreenViewModel>()
        val state = screenViewModel.state.collectAsState()
        val smsState = screenViewModel.smsState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundWhite) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    ScreenTitle(title = "رقم الهاتف") {
                        navigator.pop()
                    }
                    PhoneNumberSection(state = state.value, viewModel = screenViewModel)
                    SmsCodeSection(state = state.value, smsState = smsState.value, viewModel = screenViewModel)
                }
                CustomButton(modifier = Modifier.padding(bottom = 50.dp), text = "التحقق و المواصلة") {
                    val vetCopy = vet.copy(
                        user = vet.user.copy(
                            phoneNumber = state.value.phoneNumber
                        )
                    )
                    navigator.push(PasswordScreen(vetCopy))
                }
            }
        }
    }

    @Composable
    fun PhoneNumberSection(state: OtpScreenState, viewModel: OtpScreenViewModel) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "قم بتأكيد رقم هاتفك",
                style = MaterialTheme.typography.bodyLarge,
                color = Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(
                value = state.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                label = "رقم الهاتف",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            )
        }
    }

    @Composable
    fun SmsCodeSection(state: OtpScreenState, smsState: OtpScreenSmsState, viewModel: OtpScreenViewModel) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "قم بتأكيد رقم هاتفك",
                style = MaterialTheme.typography.bodyLarge,
                color = Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                repeat(6) { index ->
                    SmsDigitField(
                        modifier = Modifier.weight(1f),
                        value = smsState.valueOf(index),
                        onValueChange = {
                            if (it.length <= 1) {
                                viewModel.updateDigit(index, it)
                            }
                        }
                    )
                }
            }
        }
    }
}