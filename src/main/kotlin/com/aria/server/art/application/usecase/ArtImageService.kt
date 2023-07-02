package com.aria.server.art.application.usecase

import com.aria.server.art.domain.art.ArtImage

interface ArtImageService {
    fun createArtImage(image: ArtImage): ArtImage
    fun getArtImageById(id: Long): ArtImage
}
