package com.miguel.economic.gallery.model

import androidx.compose.runtime.Immutable

@Immutable
internal sealed class GalleryUiState {

    @Immutable
    data object Loading : GalleryUiState()

    @Immutable
    data class Success(
        val items: List<ReceiptViewData>
    ) : GalleryUiState()
}