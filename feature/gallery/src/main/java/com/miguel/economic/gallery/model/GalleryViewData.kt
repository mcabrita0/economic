package com.miguel.economic.gallery.model

import androidx.compose.runtime.Immutable

@Immutable
sealed class GalleryViewData {

    @Immutable
    data object Loading : GalleryViewData()

    @Immutable
    data class Success(
        val items: List<GalleryItemViewData>
    ) : GalleryViewData()
}