package com.miguel.economic.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miguel.economic.core.R
import com.miguel.economic.core.compose.ViewEventEffect
import com.miguel.economic.core.navigation.NavigationDestination
import com.miguel.economic.core.navigation.ReceiptDestination
import com.miguel.economic.gallery.model.GalleryUiState
import com.miguel.economic.gallery.model.GalleryViewEvent
import com.miguel.economic.gallery.ui.GalleryItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun GalleryScreen(
    onNavigate: (NavigationDestination) -> Unit
) {
    val viewModel = koinViewModel<GalleryViewModel>()

    val uiState by viewModel.uiState.collectAsState()

    ViewEventEffect(viewModel.viewEvent) { event ->
        when (event) {
            is GalleryViewEvent.NavigateCreateReceipt -> onNavigate(ReceiptDestination(receiptId = null))
            is GalleryViewEvent.NavigateReceipt -> onNavigate(ReceiptDestination(receiptId = event.receiptId))
        }
    }

    Box(modifier = Modifier.background(Color.White)) {
        when (val state = uiState) {
            is GalleryUiState.Loading -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.loading),
                    fontSize = 24.sp
                )
            }

            is GalleryUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.items) { data ->
                        GalleryItem(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(size = 8.dp))
                                .background(Color.LightGray)
                                .clickable(
                                    onClick = { viewModel.onClickReceipt(data) },
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = ripple()
                                ),
                            data = data
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = viewModel::onClickAddReceipt
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null
            )
        }
    }
}

