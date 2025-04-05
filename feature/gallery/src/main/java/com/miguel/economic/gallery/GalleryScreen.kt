package com.miguel.economic.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Box {
        when (val state = uiState) {
            is GalleryUiState.Loading -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Loading",
                    fontSize = 24.sp
                )
            }

            is GalleryUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.items) { data ->
                        GalleryItem(
                            modifier = Modifier.clickable { viewModel.onClickReceipt(data) },
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

