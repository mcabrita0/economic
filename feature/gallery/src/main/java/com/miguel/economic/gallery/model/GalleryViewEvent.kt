package com.miguel.economic.gallery.model

internal sealed class GalleryViewEvent {

    data object NavigateCreateReceipt : GalleryViewEvent()

    data class NavigateReceipt(val receiptId: Int) : GalleryViewEvent()
}