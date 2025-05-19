package com.nassrallah.vetfarmvet.feature_auth.presentation.login_screen

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nassrallah.vetfarmvet.MainActivity
import com.nassrallah.vetfarmvet.R
import com.nassrallah.vetfarmvet.feature_auth.presentation.signup_screen.SignupScreen
import com.nassrallah.vetfarmvet.ui.components.CustomButton
import com.nassrallah.vetfarmvet.ui.components.CustomTextField
import com.nassrallah.vetfarmvet.ui.components.HorizontalBarsAnimation
import com.nassrallah.vetfarmvet.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmvet.ui.theme.Burgundy
import com.nassrallah.vetfarmvet.ui.theme.Gray
import com.nassrallah.vetfarmvet.ui.theme.PrimaryColorRed

class LoginScreen : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<LoginScreenViewModel>()
        val state = screenViewModel.state.collectAsState()
        val form = screenViewModel.form.collectAsState()
        val context = LocalContext.current.applicationContext
        val activity = LocalContext.current as Activity
        val navigator = LocalNavigator.currentOrThrow
        
        LaunchedEffect(key1 = state.value.token) {
            if (state.value.token.isNotEmpty()) {
                val toMainActivity = Intent(activity, MainActivity::class.java)
                toMainActivity.setFlags(FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(toMainActivity)
                activity.finish()
            }
        }

        LaunchedEffect(key1 = state.value.loginResponse) {
            if (state.value.loginResponse != null) {
                val toMainActivity = Intent(activity, MainActivity::class.java)
                toMainActivity.setFlags(FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(toMainActivity)
                activity.finish()
            }
        }

        if (state.value.token.isEmpty()) {
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
                        verticalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        Text(
                            text = "مرحبا بكم!",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Burgundy
                        )
                        LoginForm(loginFormState = form.value, viewModel = screenViewModel)
                        CustomButton(text = "سجل الدخول") {
                            if (screenViewModel.validateInput()) {
                                screenViewModel.loginVet()
                            }
                        }
                        SignUpSection {
                            navigator.push(SignupScreen())
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun LoginForm(loginFormState: LoginFormState, viewModel: LoginScreenViewModel) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "قم بتسجيل الدخول",
                style = MaterialTheme.typography.bodyLarge,
                color = Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            CustomTextField(
                value = loginFormState.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                label = "رقم الهاتف",
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_profile),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(
                value = loginFormState.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = "كلمة السر",
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_lock),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )
            Text(
                text = "نسيت كلمة السر",
                style = MaterialTheme.typography.bodySmall,
                color = PrimaryColorRed,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    fun SignUpSection(onClick: () -> Unit) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "ليس لديك حساب؟", style = MaterialTheme.typography.bodyMedium, color = Gray)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "قم بالتسحيل الان!",
                style = MaterialTheme.typography.bodyLarge,
                color = PrimaryColorRed,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable { onClick() }.fillMaxWidth()
            )
        }
    }
}