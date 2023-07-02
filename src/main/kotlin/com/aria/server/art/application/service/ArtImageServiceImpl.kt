package com.aria.server.art.application.service

import com.aria.server.art.application.usecase.ArtImageService
import com.aria.server.art.domain.art.ArtImage
import com.aria.server.art.domain.exception.art.ArtImageNotFoundException
import com.aria.server.art.infrastructure.database.ArtImageRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ArtImageServiceImpl(
    private val artImageRepository: ArtImageRepository
): ArtImageService {

    override fun createArtImage(image: ArtImage): ArtImage =
        artImageRepository.save(image)

    override fun getArtImageById(id: Long): ArtImage =
        artImageRepository.findByIdOrNull(id)
            ?: throw ArtImageNotFoundException()


}
