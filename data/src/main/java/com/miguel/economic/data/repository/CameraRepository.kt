package com.miguel.economic.data.repository

import android.content.Context
import androidx.core.content.FileProvider
import com.miguel.economic.domain.repository.CameraRepository
import java.io.File

class CameraRepositoryImpl(
    private val context: Context
) : CameraRepository {

    override fun createPhotoFile(): String {
        val filename = "${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, filename)

        return FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".generic.provider",
            file
        ).toString()
    }
}