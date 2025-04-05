package com.miguel.economic.domain.usecase

import com.miguel.economic.domain.repository.CameraRepository

class CreatePhotoFileUseCase(
    private val cameraRepository: CameraRepository
) {

    operator fun invoke(): String {
        return cameraRepository.createPhotoFile()
    }
}