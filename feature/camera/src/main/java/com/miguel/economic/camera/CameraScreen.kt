package com.miguel.economic.camera

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.miguel.economic.core.navigation.GalleryDestination
import com.miguel.economic.core.navigation.NavigationDestination

@Composable
fun CameraScreen(
    onNavigate: (NavigationDestination) -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onNavigate(GalleryDestination)
            },
        text = "camera"
    )
}