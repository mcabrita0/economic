package com.miguel.economic

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miguel.economic.camera.CameraScreen
import com.miguel.economic.core.navigation.CameraDestination
import com.miguel.economic.core.navigation.GalleryDestination
import com.miguel.economic.gallery.GalleryScreen

@Composable
internal fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = GalleryDestination
    ) {
        composable<GalleryDestination> {
            GalleryScreen(onNavigate = navController::navigate)
        }

        composable<CameraDestination> {
            CameraScreen(onNavigate = navController::navigate)
        }
    }
}