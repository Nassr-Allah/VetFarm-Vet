package com.nassrallah.vetfarmvet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import cafe.adriel.voyager.navigator.Navigator
import com.nassrallah.vetfarmvet.feature_requests.presentation.requests_screen.RequestsScreen
import com.nassrallah.vetfarmvet.ui.theme.VetFarmVetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VetFarmVetTheme {
                // A surface container using the 'background' color from the theme
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Navigator(RequestsScreen())
                }
            }
        }
    }
}
