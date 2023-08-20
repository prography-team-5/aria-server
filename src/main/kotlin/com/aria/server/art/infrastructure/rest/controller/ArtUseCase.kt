package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.CreateArtImageResponse
import com.aria.server.art.infrastructure.rest.dto.CreateArtRequest
import com.aria.server.art.infrastructure.rest.dto.CreateArtResponse
import com.aria.server.art.infrastructure.rest.dto.EditArtDescriptionRequest
import com.aria.server.art.infrastructure.rest.dto.EditArtImagesRequest
import com.aria.server.art.infrastructure.rest.dto.EditArtSizeRequest
import com.aria.server.art.infrastructure.rest.dto.EditArtStyleRequest
import com.aria.server.art.infrastructure.rest.dto.EditArtTagsRequest
import com.aria.server.art.infrastructure.rest.dto.EditArtTitleRequest
import com.aria.server.art.infrastructure.rest.dto.EditArtYearRequest
import com.aria.server.art.infrastructure.rest.dto.GetArtResponseDto
import com.aria.server.art.infrastructure.rest.dto.GetRandomArtResponseDto
import com.aria.server.art.infrastructure.rest.dto.SimpleArtDto
import org.springframework.web.multipart.MultipartFile

interface ArtUseCase {
    fun createArt(dto: CreateArtRequest): CreateArtResponse
    fun createArtImage(image: MultipartFile): CreateArtImageResponse
    fun getRandomArts(count: Int): List<GetRandomArtResponseDto>
    fun getArts(artistId: Long, page: Int, count: Int): List<SimpleArtDto>
    fun getMyArts(page: Int, count: Int): List<SimpleArtDto>
    fun getArt(artId: Long): GetArtResponseDto

    fun editArtImages(dto: EditArtImagesRequest)
    fun editArtTitle(dto: EditArtTitleRequest)
    fun editArtYear(dto: EditArtYearRequest)
    fun editArtSize(dto: EditArtSizeRequest)
    fun editArtStyle(dto: EditArtStyleRequest)

    fun editArtTags(dto: EditArtTagsRequest)
    fun editArtDescription(dto: EditArtDescriptionRequest)
}
