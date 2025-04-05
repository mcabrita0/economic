package com.miguel.economic.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus

@Composable
fun <T> ViewEventEffect(
    source: Flow<T>,
    onEvent: (T) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(lifecycle, source) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            source.onEach(onEvent).launchIn(this + Dispatchers.Main.immediate)
        }
    }
}