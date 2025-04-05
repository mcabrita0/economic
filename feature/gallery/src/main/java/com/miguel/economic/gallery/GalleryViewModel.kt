package com.miguel.economic.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.usecase.GetReceiptsUseCase
import com.miguel.economic.gallery.mapper.toViewData
import com.miguel.economic.gallery.model.GalleryUiState
import com.miguel.economic.gallery.model.GalleryViewEvent
import com.miguel.economic.gallery.model.GalleryViewEvent.NavigateCreateReceipt
import com.miguel.economic.gallery.model.GalleryViewEvent.NavigateReceipt
import com.miguel.economic.gallery.model.ReceiptViewData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class GalleryViewModel(
    private val getReceiptsUseCase: GetReceiptsUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<GalleryUiState>(GalleryUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _viewEvent = Channel<GalleryViewEvent>()
    val viewEvent = _viewEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            loadReceipts()
        }
    }

    fun onClickAddReceipt() {
        viewModelScope.launch(ioDispatcher) {
            _viewEvent.send(NavigateCreateReceipt)
        }
    }

    fun onClickReceipt(item: ReceiptViewData) {
        if (item.id != null) {
            viewModelScope.launch(ioDispatcher) {
                _viewEvent.send(NavigateReceipt(receiptId = item.id))
            }
        }
    }

    private suspend fun loadReceipts() = withContext(ioDispatcher) {
        _uiState.value = GalleryUiState.Loading
        _uiState.value = GalleryUiState.Success(
            items = getReceiptsUseCase().map(ReceiptModel::toViewData)
        )
    }
}