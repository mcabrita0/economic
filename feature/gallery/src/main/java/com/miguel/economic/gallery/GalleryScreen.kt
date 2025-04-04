package com.miguel.economic.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.miguel.economic.core.navigation.CameraDestination
import com.miguel.economic.core.navigation.NavigationDestination

@Composable
fun GalleryScreen(
    onNavigate: (NavigationDestination) -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onNavigate(CameraDestination)
            },
        text = "gallery"
    )
}