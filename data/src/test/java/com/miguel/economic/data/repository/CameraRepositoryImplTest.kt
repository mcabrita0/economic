package com.miguel.economic.data.repository

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class CameraRepositoryImplTest {

    private val context: Context = mockk(relaxed = true)

    private val cameraRepository = CameraRepositoryImpl(
        context = context
    )

    @Test
    fun `When createPhotoFile is called then return a new file uri`() {
        // Given
        mockkStatic(FileProvider::class)
        val fileDir = File("fileDir")
        val fileUri = "fileUri"

        every { context.filesDir } returns fileDir
        every { FileProvider.getUriForFile(any(), any(), any()) } returns mockk<Uri> {
            every { this@mockk.toString() } returns fileUri
        }

        // When
        val result = cameraRepository.createPhotoFile()

        // Then
        assertEquals(fileUri, result)
        unmockkStatic(FileProvider::class)
    }
}