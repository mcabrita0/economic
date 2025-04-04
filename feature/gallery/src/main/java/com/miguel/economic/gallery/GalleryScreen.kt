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
import com.miguel.economic.core.compose.NavigationEffect
import com.miguel.economic.core.navigation.CameraDestination
import com.miguel.economic.core.navigation.NavigationDestination
import com.miguel.economic.gallery.model.GalleryViewData
import com.miguel.economic.gallery.ui.GalleryItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun GalleryScreen(
    onNavigate: (NavigationDestination) -> Unit
) {
    val viewModel = koinViewModel<GalleryViewModel>()

    val uiState by viewModel.uiState.collectAsState()

    NavigationEffect(
        source = viewModel.navigation,
        onNavigate = onNavigate
    )

    Box {
        when (val state = uiState) {
            is GalleryViewData.Loading -> {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onNavigate(CameraDestination)
                        },
                    text = "gallery"
                )
            }

            is GalleryViewData.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.items) { data ->
                        GalleryItem(data = data)
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

