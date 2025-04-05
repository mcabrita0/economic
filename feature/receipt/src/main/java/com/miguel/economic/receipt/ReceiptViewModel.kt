package com.miguel.economic.receipt

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguel.economic.core.R
import com.miguel.economic.core.navigation.ReceiptDestination
import com.miguel.economic.core.util.CurrencyUtil
import com.miguel.economic.domain.usecase.CreateOrUpdateReceiptUseCase
import com.miguel.economic.domain.usecase.CreatePhotoFileUseCase
import com.miguel.economic.domain.usecase.GetReceiptUseCase
import com.miguel.economic.receipt.mapper.toModel
import com.miguel.economic.receipt.mapper.toViewData
import com.miguel.economic.receipt.model.CurrencyDialogUiState
import com.miguel.economic.receipt.model.ReceiptUiState
import com.miguel.economic.receipt.model.ReceiptViewData
import com.miguel.economic.receipt.model.ReceiptViewEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

internal class ReceiptViewModel(
    private val args: ReceiptDestination,
    private val createOrUpdateReceiptUseCase: CreateOrUpdateReceiptUseCase,
    private val createPhotoFileUseCase: CreatePhotoFileUseCase,
    private val getReceiptUseCase: GetReceiptUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<ReceiptUiState>(ReceiptUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _viewEvent = Channel<ReceiptViewEvent>()
    val viewEvent = _viewEvent.receiveAsFlow()

    private val _currencyDialogUiState = MutableStateFlow<CurrencyDialogUiState>(CurrencyDialogUiState.Hide)
    val currencyDialogUiState = _currencyDialogUiState.asStateFlow()

    private var pendingPhotoFilename: String? = null

    private var clickPhotoJob: Job? = null

    init {
        viewModelScope.launch {
            loadRecipe(id = args.receiptId)
        }
    }

    fun onClickPhoto() {
        if (_uiState.value is ReceiptUiState.Loading || clickPhotoJob?.isActive == true || pendingPhotoFilename != null) {
            return
        }

        viewModelScope.launch(ioDispatcher) {
            _viewEvent.send(
                ReceiptViewEvent.TakePicture(
                    filename = createPhotoFileUseCase().also {
                        pendingPhotoFilename = it
                    }
                )
            )
        }
    }

    fun onClickBack() {
        viewModelScope.launch(ioDispatcher) {
            _viewEvent.send(ReceiptViewEvent.Back)
        }
    }

    fun onClickSave() {
        viewModelScope.launch(ioDispatcher) {
            val receipt = (_uiState.value as? ReceiptUiState.Success)?.receipt ?: return@launch

            val receiptModel = receipt.toModel(id = args.receiptId)

            if (receiptModel != null) {
                createOrUpdateReceiptUseCase(receipt = receiptModel)
                _viewEvent.send(ReceiptViewEvent.SaveAndExit)
            } else {
                val errorMessage = when {
                    receipt.photoFilename == null -> R.string.error_no_photo
                    receipt.amount.isEmpty() || receipt.currencyCode.isEmpty() -> R.string.error_no_currency
                    receipt.createdDate == null -> R.string.error_no_date
                    else -> null
                }

                if (errorMessage != null) {
                    _viewEvent.send(ReceiptViewEvent.Error(message = errorMessage))
                }
            }
        }
    }

    fun onClickCurrency() {
        viewModelScope.launch(ioDispatcher) {
            val receipt = (_uiState.value as? ReceiptUiState.Success)?.receipt ?: return@launch

            _currencyDialogUiState.value = CurrencyDialogUiState.Show(
                currencyCodes = CurrencyUtil.allCurrencyCodes(),
                currencyCode = receipt.currencyCode,
                amount = receipt.amount
            )
        }
    }

    fun onCurrencyDialogDismiss() {
        viewModelScope.launch(ioDispatcher) {
            val update = _currencyDialogUiState.value as? CurrencyDialogUiState.Show ?: return@launch

            _uiState.update {
                (it as? ReceiptUiState.Success)?.copy(
                    receipt = it.receipt.copy(
                        amount = update.amount,
                        currencyCode = update.currencyCode
                    )
                ) ?: it
            }

            _currencyDialogUiState.value = CurrencyDialogUiState.Hide
        }
    }

    fun onCurrencyAmountChange(amount: String) {
        if (amount.toFloatOrNull() == null && amount.isNotEmpty()) {
            return
        }

        viewModelScope.launch(ioDispatcher) {
            _currencyDialogUiState.update {
                (it as? CurrencyDialogUiState.Show)?.copy(
                    amount = amount
                ) ?: it
            }
        }
    }

    fun onCurrencyCodeChange(currencyCode: String) {
        viewModelScope.launch(ioDispatcher) {
            _currencyDialogUiState.update {
                (it as? CurrencyDialogUiState.Show)?.copy(
                    currencyCode = currencyCode
                ) ?: it
            }
        }
    }

    @SuppressLint("NewApi")
    fun onPhotoTaken(success: Boolean) {
        val filename = pendingPhotoFilename

        if (_uiState.value is ReceiptUiState.Loading || filename == null || !success) {
            pendingPhotoFilename = null
            return
        }

        viewModelScope.launch(ioDispatcher) {
            _uiState.update {
                val state = it as? ReceiptUiState.Success
                state?.copy(
                    receipt = state.receipt.copy(
                        photoFilename = pendingPhotoFilename,
                        createdDate = LocalDateTime.now()
                    ),
                ) ?: it
            }

            pendingPhotoFilename = null
        }
    }

    private suspend fun loadRecipe(id: Int?) = withContext(ioDispatcher) {
        _uiState.value = ReceiptUiState.Loading

        _uiState.value = ReceiptUiState.Success(
            receipt = if (id != null) {
                getReceiptUseCase(id = id).toViewData()
            } else {
                ReceiptViewData()
            }
        )
    }
}