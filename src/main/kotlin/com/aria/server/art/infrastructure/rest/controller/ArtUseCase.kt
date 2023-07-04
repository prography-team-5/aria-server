package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.CreateArtImageResponse
import com.aria.server.art.infrastructure.rest.dto.CreateArtRequest
import com.aria.server.art.infrastructure.rest.dto.CreateArtResponse
import com.aria.server.art.infrastructure.rest.dto.GetArtResponseDto
import com.aria.server.art.infrastructure.rest.dto.GetRandomArtResponse
import com.aria.server.art.infrastructure.rest.dto.SimpleArtDto
import org.springframework.web.multipart.MultipartFile

interface ArtUseCase {
    fun createArt(dto: CreateArtRequest): CreateArtResponse
    fun createArtImage(image: MultipartFile): CreateArtImageResponse
    fun getRandomArt(): GetRandomArtResponse
    fun getArts(artistId: Long, page: Int, size: Int): List<SimpleArtDto>
    fun getMyArts(page: Int, size: Int): List<SimpleArtDto>
    fun getArt(artId: Long): GetArtResponseDto
}
