package com.miguel.economic.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguel.economic.core.navigation.CameraDestination
import com.miguel.economic.core.navigation.NavigationDestination
import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.usecase.GetReceiptsUseCase
import com.miguel.economic.gallery.mapper.toViewData
import com.miguel.economic.gallery.model.GalleryViewData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
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

    private val _uiState = MutableStateFlow<GalleryViewData>(GalleryViewData.Loading)
    val uiState = _uiState.asStateFlow()

    private val _navigation = Channel<NavigationDestination>()
    val navigation = _navigation.receiveAsFlow()

    private var clickAddReceiptJob: Job? = null

    init {
        viewModelScope.launch {
            loadReceipts()
        }
    }

    fun onClickAddReceipt() {
        if (clickAddReceiptJob?.isActive == true) {
            return
        }

        clickAddReceiptJob = viewModelScope.launch {
            _navigation.send(CameraDestination)
        }
    }

    private suspend fun loadReceipts() = withContext(ioDispatcher) {
        _uiState.value = GalleryViewData.Loading
        _uiState.value = GalleryViewData.Success(
            items = getReceiptsUseCase().map(ReceiptModel::toViewData)
        )
    }
}