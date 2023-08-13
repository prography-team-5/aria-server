package com.aria.server.art.application.usecase

import org.springframework.web.multipart.MultipartFile

interface S3Service {
    fun uploadImage(image: MultipartFile): String
    fun deleteImage(imageName: String)
}
