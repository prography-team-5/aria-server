package com.aria.server.art.infrastructure

import com.aria.server.art.infrastructure.dto.CreateArtImageResponse
import com.aria.server.art.infrastructure.dto.CreateArtRequest
import com.aria.server.art.infrastructure.dto.CreateArtResponse
import com.aria.server.art.infrastructure.dto.GetRandomArtResponse
import org.springframework.web.multipart.MultipartFile


interface ArtService {
    fun createArt(dto: CreateArtRequest): CreateArtResponse

    fun createArtImage(image: MultipartFile): CreateArtImageResponse
    fun getRandomArt(): GetRandomArtResponse
}
