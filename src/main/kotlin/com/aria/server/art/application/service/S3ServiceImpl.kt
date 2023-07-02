package com.aria.server.art.application.service

import com.amazonaws.services.s3.model.ObjectMetadata
import com.aria.server.art.application.usecase.S3Service
import com.aria.server.art.domain.exception.art.ArtImageS3Exception
import com.aria.server.art.infrastructure.s3.S3Api
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*

@Service
class S3ServiceImpl(
    private val s3Api: S3Api
): S3Service {

    override fun uploadImage(image: MultipartFile): String {
        val objectKey = UUID.randomUUID().toString()
        val metadata = ObjectMetadata().apply {
            contentType = image.contentType
            contentLength = image.size
        }
        val imageStream = try {
            image.inputStream
        } catch (e: IOException) {
            throw ArtImageS3Exception()
        }
        return s3Api.saveImage(objectKey, metadata, imageStream)
    }
}
