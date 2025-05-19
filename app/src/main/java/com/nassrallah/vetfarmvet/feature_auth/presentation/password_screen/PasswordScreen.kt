package com.nassrallah.vetfarmvet.feature_auth.presentation.password_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nassrallah.vetfarmvet.feature_auth.data.dto.VetDTO
import com.nassrallah.vetfarmvet.ui.components.CustomButton
import com.nassrallah.vetfarmvet.ui.components.CustomTextField
import com.nassrallah.vetfarmvet.ui.components.HorizontalBarsAnimation
import com.nassrallah.vetfarmvet.ui.components.ScreenTitle
import com.nassrallah.vetfarmvet.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmvet.ui.theme.Gray
import com.nassrallah.vetfarmvet.ui.theme.PrimaryColorRed

data class PasswordScreen(private val vet: VetDTO) : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<PasswordScreenViewModel>()
        val state = screenViewModel.state.collectAsState()
        val form = screenViewModel.form.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current.applicationContext

        LaunchedEffect(key1 = state.value.isAccountCreated) {
            if (state.value.isAccountCreated) {
                Toast.makeText(context, "تم انشاء الحساب", Toast.LENGTH_LONG).show()
                navigator.popUntilRoot()
            }
        }

        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundWhite) {
            if (state.value.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    HorizontalBarsAnimation(color = PrimaryColorRed, size = 30f)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        ScreenTitle(title = "كلمة السر") {
                            navigator.pop()
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        PasswordSection(form = form.value, viewModel = screenViewModel)
                    }
                    CustomButton(text = "انشئ حسابك") {
                        if (screenViewModel.validateInput()) {
                            screenViewModel.createVet(vet)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun PasswordSection(form: PasswordScreenForm, viewModel: PasswordScreenViewModel) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "اختر كلمة السر الخاصة بك",
                style = MaterialTheme.typography.bodyLarge,
                color = Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(
                value = form.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = "كلمة السر",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(
                value = form.confirmPassword,
                onValueChange = { viewModel.updateConfirmPassword(it) },
                label = "أعد ادخال كلمة السر",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
        }
    }

}