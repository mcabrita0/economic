package com.miguel.economic.receipt

import app.cash.turbine.test
import com.miguel.economic.core.navigation.ReceiptDestination
import com.miguel.economic.core.util.CurrencyUtil
import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.usecase.CreateOrUpdateReceiptUseCase
import com.miguel.economic.domain.usecase.CreatePhotoFileUseCase
import com.miguel.economic.domain.usecase.GetReceiptUseCase
import com.miguel.economic.receipt.mapper.toDialogUiState
import com.miguel.economic.receipt.mapper.toViewData
import com.miguel.economic.receipt.model.CurrencyDialogUiState
import com.miguel.economic.receipt.model.ReceiptUiState
import com.miguel.economic.receipt.model.ReceiptUiState.Loading
import com.miguel.economic.receipt.model.ReceiptViewData
import com.miguel.economic.receipt.model.ReceiptViewEvent
import com.miguel.economic.receipt.model.ReceiptViewEvent.SaveAndExit
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class ReceiptViewModelTest {

    private val createOrUpdateReceiptUseCase: CreateOrUpdateReceiptUseCase = mockk(relaxed = true)
    private val createPhotoFileUseCase: CreatePhotoFileUseCase = mockk()
    private val getReceiptUseCase: GetReceiptUseCase = mockk()
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    private val receipt by lazy {
        ReceiptModel(id = 1, photoFilename = "123", amount = 1f, currencyCode = "EUR", createdDate = LocalDateTime.now())
    }

    private lateinit var viewModel: ReceiptViewModel

    @Test
    fun `When ViewModel is created then load receipt`() = runTest(dispatcher) {
        // Given
        val completable = CompletableDeferred<Unit>()
        coEvery { getReceiptUseCase(any()) } coAnswers {
            completable.await()
            receipt
        }

        val expected = ReceiptUiState.Success(receipt = receipt.toViewData())

        // When
        initViewModel(args = ReceiptDestination(receiptId = 1))

        // Then
        viewModel.uiState.test {
            assertEquals(Loading, awaitItem())
            completable.complete(Unit)
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `When photo is clicked and view is loading then do nothing`() = runTest(dispatcher) {
        // Given
        val completable = CompletableDeferred<Unit>()
        coEvery { getReceiptUseCase(any()) } coAnswers {
            completable.await()
            receipt
        }
        initViewModel(args = ReceiptDestination(receiptId = 1))

        // When
        viewModel.onClickPhoto()

        // Then
        viewModel.viewEvent.test {
            expectNoEvents()
        }
        completable.complete(Unit)
    }

    @Test
    fun `When photo is clicked and job is ongoing then do nothing`() = runTest(dispatcher) {
        // Given
        val completable = CompletableDeferred<Unit>()
        val photoFilename = "123"
        coEvery { createPhotoFileUseCase() } coAnswers {
            completable.await()
            photoFilename
        }
        initViewModel(args = ReceiptDestination(receiptId = null))
        viewModel.onClickPhoto()

        val expected = ReceiptViewEvent.TakePicture(filename = photoFilename)

        // When
        viewModel.onClickPhoto()
        completable.complete(Unit)

        // Then
        viewModel.viewEvent.test {
            assertEquals(expected, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `When photo is clicked and camera hasn't returned then do nothing`() = runTest(dispatcher) {
        // Given
        val photoFilename = "123"
        coEvery { createPhotoFileUseCase() } returns photoFilename
        initViewModel(args = ReceiptDestination(receiptId = null))
        viewModel.onClickPhoto()

        val expected = ReceiptViewEvent.TakePicture(filename = photoFilename)

        // When
        viewModel.onClickPhoto()

        // Then
        viewModel.viewEvent.test {
            assertEquals(expected, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `When back is clicked then emit event`() = runTest(dispatcher) {
        // Given
        initViewModel(args = ReceiptDestination(receiptId = null))

        // When
        viewModel.onClickBack()

        // Then
        viewModel.viewEvent.test {
            assertEquals(ReceiptViewEvent.Back, awaitItem())
        }
    }

    @Test
    fun `When save is clicked and view is loading then do nothing`() = runTest(dispatcher) {
        // Given
        val completable = CompletableDeferred<Unit>()
        coEvery { getReceiptUseCase(any()) } coAnswers {
            completable.await()
            receipt
        }
        initViewModel(args = ReceiptDestination(receiptId = 1))

        // When
        viewModel.onClickSave()

        // Then
        viewModel.viewEvent.test {
            expectNoEvents()
        }
        completable.complete(Unit)
    }

    @Test
    fun `When save is clicked and receipt has incomplete fields then emit error`() = runTest(dispatcher) {
        // Given
        initViewModel(args = ReceiptDestination(receiptId = null))

        // When
        viewModel.onClickSave()

        // Then
        viewModel.viewEvent.test {
            assertTrue(awaitItem() is ReceiptViewEvent.Error)
        }
    }

    @Test
    fun `When save is clicked and receipt is valid then create recipe and emit save`() = runTest(dispatcher) {
        // Given
        coEvery { getReceiptUseCase(any()) } returns receipt
        initViewModel(args = ReceiptDestination(receiptId = 1))

        // When
        viewModel.onClickSave()

        // Then
        viewModel.viewEvent.test {
            assertEquals(SaveAndExit, awaitItem())
        }
        coVerify { createOrUpdateReceiptUseCase(receipt) }
    }

    @Test
    fun `When currency is clicked and view is loading then do nothing`() = runTest(dispatcher) {
        // Given
        val completable = CompletableDeferred<Unit>()
        coEvery { getReceiptUseCase(any()) } coAnswers {
            completable.await()
            receipt
        }
        initViewModel(args = ReceiptDestination(receiptId = 1))

        // When
        viewModel.onClickCurrency()

        // Then
        viewModel.currencyDialogUiState.test {
            assertEquals(CurrencyDialogUiState.Hide, awaitItem())
        }
        completable.complete(Unit)
    }

    @Test
    fun `When currency is clicked then show dialog`() = runTest(dispatcher) {
        // Given
        coEvery { getReceiptUseCase(any()) } returns receipt
        initViewModel(args = ReceiptDestination(receiptId = 1))

        val viewData = receipt.toViewData()
        val expected = CurrencyDialogUiState.Show(
            currencyCodes = CurrencyUtil.allCurrencyCodes(),
            currencyCode = viewData.currencyCode,
            amount = viewData.amount
        )

        // When
        viewModel.onClickCurrency()
        advanceUntilIdle()

        // Then
        viewModel.currencyDialogUiState.test {
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `When currency dialog is dismissed then update state and dismiss dialog`() = runTest(dispatcher) {
        // Given
        val newAmount = "125"
        val newCurrencyCode = "EUR"
        initViewModel(args = ReceiptDestination(receiptId = null))
        viewModel.onClickCurrency()
        advanceUntilIdle()
        viewModel.onCurrencyAmountChange(amount = newAmount)
        viewModel.onCurrencyCodeChange(currencyCode = newCurrencyCode)
        advanceUntilIdle()

        val expected = ReceiptUiState.Success(
            receipt = ReceiptViewData(
                amount = newAmount,
                currencyCode = newCurrencyCode
            )
        )

        // When
        viewModel.onCurrencyDialogDismiss()
        advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            assertEquals(expected, awaitItem())
        }
        viewModel.currencyDialogUiState.test {
            assertEquals(CurrencyDialogUiState.Hide, awaitItem())
        }
    }

    @Test
    fun `When currency amount changes to invalid number then do nothing`() = runTest(dispatcher) {
        // Given
        val invalidAmount = "12aaa"
        initViewModel(args = ReceiptDestination(receiptId = null))
        viewModel.onClickCurrency()
        advanceUntilIdle()

        val expected = ReceiptViewData().toDialogUiState()

        // When
        viewModel.onCurrencyAmountChange(amount = invalidAmount)
        advanceUntilIdle()

        // Then
        viewModel.currencyDialogUiState.test {
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `When currency amount changes and is valid then update dialog state`() = runTest(dispatcher) {
        // Given
        val newAmount = "123"
        initViewModel(args = ReceiptDestination(receiptId = null))
        viewModel.onClickCurrency()
        advanceUntilIdle()

        val expected = ReceiptViewData(
            amount = newAmount
        ).toDialogUiState()

        // When
        viewModel.onCurrencyAmountChange(amount = newAmount)
        advanceUntilIdle()

        // Then
        viewModel.currencyDialogUiState.test {
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `When currency code changes then update dialog state`() = runTest(dispatcher) {
        // Given
        val newCurrencyCode = "EUR"
        initViewModel(args = ReceiptDestination(receiptId = null))
        viewModel.onClickCurrency()
        advanceUntilIdle()

        val expected = ReceiptViewData(
            currencyCode = newCurrencyCode
        ).toDialogUiState()

        // When
        viewModel.onCurrencyCodeChange(currencyCode = newCurrencyCode)
        advanceUntilIdle()

        // Then
        viewModel.currencyDialogUiState.test {
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `When a photo is taken and view is loading then do nothing`() = runTest(dispatcher) {
        // Given
        val completable = CompletableDeferred<Unit>()
        coEvery { getReceiptUseCase(any()) } coAnswers {
            completable.await()
            receipt
        }
        initViewModel(args = ReceiptDestination(receiptId = 1))

        // When
        viewModel.onPhotoTaken()

        // Then
        viewModel.uiState.test {
            assertEquals(Loading, awaitItem())
            expectNoEvents()
        }
        completable.complete(Unit)
    }

    @Test
    fun `When a photo is taken but no file was created then do nothing`() = runTest(dispatcher) {
        // Given
        initViewModel(args = ReceiptDestination(receiptId = null))

        val expected = ReceiptUiState.Success(ReceiptViewData())

        // When
        viewModel.onPhotoTaken()

        // Then
        viewModel.uiState.test {
            assertEquals(expected, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun `When a photo is taken then update state with filename and date`() = runTest(dispatcher) {
        // Given
        mockkStatic(LocalDateTime::class)

        val date = LocalDateTime.MIN
        val filename = "file"

        every { LocalDateTime.now() } returns date
        coEvery { createPhotoFileUseCase() } returns filename

        initViewModel(args = ReceiptDestination(receiptId = null))
        viewModel.onClickPhoto()
        advanceUntilIdle()

        val expected = ReceiptUiState.Success(
            receipt = ReceiptViewData(
                photoFilename = filename,
                createdDate = date
            )
        )

        // When
        viewModel.onPhotoTaken()
        advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            assertEquals(expected, awaitItem())
        }

        unmockkStatic(LocalDateTime::class)
    }


    private fun TestScope.initViewModel(args: ReceiptDestination) {
        viewModel = ReceiptViewModel(
            args = args,
            createOrUpdateReceiptUseCase = createOrUpdateReceiptUseCase,
            createPhotoFileUseCase = createPhotoFileUseCase,
            getReceiptUseCase = getReceiptUseCase,
            ioDispatcher = dispatcher
        )
        advanceUntilIdle()
    }
}