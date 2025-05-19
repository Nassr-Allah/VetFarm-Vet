package com.nassrallah.vetfarmvet.feature_requests.presentation.requests_screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.nassrallah.vetfarmvet.R
import com.nassrallah.vetfarmvet.feature_requests.data.dto.VetRequestDTO
import com.nassrallah.vetfarmvet.ui.components.DetailRow
import com.nassrallah.vetfarmvet.ui.components.HorizontalBarsAnimation
import com.nassrallah.vetfarmvet.ui.components.ItemCard
import com.nassrallah.vetfarmvet.ui.components.ScreenTitle
import com.nassrallah.vetfarmvet.ui.theme.BackgroundWhite
import com.nassrallah.vetfarmvet.ui.theme.Green
import com.nassrallah.vetfarmvet.ui.theme.OffBlack
import com.nassrallah.vetfarmvet.ui.theme.PrimaryColorRed

class RequestsScreen : Screen {

    @Composable
    override fun Content() {

        val screenViewModel = getViewModel<RequestsScreenViewModel>()
        val screenState = screenViewModel.requestsScreenState.collectAsState()

        val context = LocalContext.current.applicationContext

        LaunchedEffect(key1 = screenState.value.token) {
            if (screenState.value.token != null) {
                screenViewModel.getVetRequests()
            }
        }

        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundWhite) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(35.dp)
            ) {
                ScreenTitle(title = "طلبات التصريح") {

                }
                if (screenState.value.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        HorizontalBarsAnimation(color = PrimaryColorRed, size = 25f)
                    }
                } else {
                    RequestsListSection(
                        requests = screenViewModel.vetRequestsList.value,
                        onClick = { },
                        onCall = {
                            val uri = Uri.parse("tel:$it")
                            val dialIntent = Intent(Intent.ACTION_DIAL, uri).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(dialIntent)
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun RequestsListSection(
        modifier: Modifier = Modifier,
        requests: List<VetRequestDTO>,
        onClick: (Int) -> Unit,
        onCall: (String) -> Unit
    ) {
        LazyColumn(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(requests) { request ->
                val name = "${request.seller?.user?.firstName} ${request.seller?.user?.lastName}"
                ItemCard(
                    topSection = {
                        Text(text = name, color = OffBlack, style = MaterialTheme.typography.titleMedium)
                    },
                    midSection = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "اتصل بالبائع", color = OffBlack, style = MaterialTheme.typography.bodyMedium)
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable {
                                        onCall(request.seller.user.phoneNumber)
                                    }
                                    .background(BackgroundWhite)
                                    .padding(10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_phone),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = Green
                                )
                            }
                        }
                    },
                    onClick = { onClick(request.id ?: return@ItemCard) }
                )
            }
        }
    }
}