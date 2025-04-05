package com.miguel.economic

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.miguel.economic.core.navigation.GalleryDestination
import com.miguel.economic.core.navigation.ReceiptDestination
import com.miguel.economic.gallery.GalleryScreen
import com.miguel.economic.receipt.ReceiptScreen

@Composable
internal fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.safeDrawingPadding(),
        navController = navController,
        startDestination = GalleryDestination
    ) {
        composable<GalleryDestination> {
            GalleryScreen(onNavigate = navController::navigate)
        }

        composable<ReceiptDestination> {
            val args = it.toRoute<ReceiptDestination>()
            ReceiptScreen(
                args = args,
                onNavigate = navController::navigate
            )
        }
    }
}