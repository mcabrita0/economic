package com.miguel.economic.domain.usecase

import com.miguel.economic.domain.repository.CameraRepository
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class CreatePhotoFileUseCaseTest {

    private val cameraRepository: CameraRepository = mockk()

    private val createPhotoFileUseCase = CreatePhotoFileUseCase(
        cameraRepository = cameraRepository
    )

    @Test
    fun `When invoked then return value from repository`() {
        // Given
        val filename = "filename21234"
        coEvery { cameraRepository.createPhotoFile() } returns filename

        // When
        val result = createPhotoFileUseCase()

        // Then
        assertEquals(filename, result)
    }
}