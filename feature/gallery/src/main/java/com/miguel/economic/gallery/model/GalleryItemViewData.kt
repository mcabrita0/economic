package com.miguel.economic.gallery.model

import androidx.compose.runtime.Immutable

@Immutable
data class GalleryItemViewData(
    val photoFilename: String,
    val amount: String,
    val createdDate: String
)