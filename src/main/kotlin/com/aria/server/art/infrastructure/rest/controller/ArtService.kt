package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.CreateArtImageResponse
import com.aria.server.art.infrastructure.rest.dto.CreateArtRequest
import com.aria.server.art.infrastructure.rest.dto.CreateArtResponse
import com.aria.server.art.infrastructure.rest.dto.GetRandomArtResponse
import org.springframework.web.multipart.MultipartFile


interface ArtService {
    fun createArt(dto: CreateArtRequest): CreateArtResponse
    fun createArtImage(image: MultipartFile): CreateArtImageResponse
    fun getRandomArt(): GetRandomArtResponse
}
