package com.miguel.economic.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.miguel.economic.core.navigation.NavigationDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus

@Composable
fun NavigationEffect(
    source: Flow<NavigationDestination>,
    onNavigate: (NavigationDestination) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(lifecycle, source) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            source.onEach(onNavigate).launchIn(this + Dispatchers.Main.immediate)
        }
    }
}