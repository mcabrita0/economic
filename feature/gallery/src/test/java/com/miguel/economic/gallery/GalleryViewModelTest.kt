package com.miguel.economic.gallery

import app.cash.turbine.test
import com.miguel.economic.domain.model.ReceiptModel
import com.miguel.economic.domain.usecase.GetReceiptsUseCase
import com.miguel.economic.gallery.mapper.toViewData
import com.miguel.economic.gallery.model.GalleryUiState
import com.miguel.economic.gallery.model.GalleryUiState.Loading
import com.miguel.economic.gallery.model.GalleryViewEvent.NavigateCreateReceipt
import com.miguel.economic.gallery.model.GalleryViewEvent.NavigateReceipt
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class GalleryViewModelTest {

    private val getReceiptsUseCase: GetReceiptsUseCase = mockk(relaxed = true)
    private val dispatcher = StandardTestDispatcher()

    private val receipt by lazy {
        ReceiptModel(photoFilename = "", amount = 1f, currencyCode = "", createdDate = LocalDateTime.now())
    }

    private lateinit var viewModel: GalleryViewModel

    @Test
    fun `When ViewModel is created then load receipts`() = runTest(dispatcher) {
        // Given
        val completable = CompletableDeferred<Unit>()
        val receipts = listOf(receipt)
        coEvery { getReceiptsUseCase() } coAnswers {
            completable.await()
            receipts
        }

        val expected = GalleryUiState.Success(items = receipts.map { it.toViewData() })

        // When
        initViewModel()

        // Then
        viewModel.uiState.test {
            assertEquals(Loading, awaitItem())
            completable.complete(Unit)
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `When add receipt is clicked then emit navigation event`() = runTest(dispatcher) {
        // Given
        initViewModel()

        // When
        viewModel.onClickAddReceipt()

        // Then
        viewModel.viewEvent.test {
            assertEquals(NavigateCreateReceipt, awaitItem())
        }
    }

    @Test
    fun `When receipt is clicked with non-null id then emit navigation event`() = runTest(dispatcher) {
        // Given
        val id = 1
        val nonNullReceipt = receipt.copy(id = id).toViewData()
        initViewModel()
        val expected = NavigateReceipt(receiptId = id)

        // When
        viewModel.onClickReceipt(nonNullReceipt)

        // Then
        viewModel.viewEvent.test {
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `When receipt is clicked with null id then do nothing`() = runTest(dispatcher) {
        // Given
        val nullReceipt = receipt.copy(id = null).toViewData()
        initViewModel()

        // When
        viewModel.onClickReceipt(nullReceipt)

        // Then
        viewModel.viewEvent.test {
            expectNoEvents()
        }
    }

    private fun TestScope.initViewModel() {
        viewModel = GalleryViewModel(
            getReceiptsUseCase = getReceiptsUseCase,
            ioDispatcher = dispatcher
        )
        advanceUntilIdle()
    }
}