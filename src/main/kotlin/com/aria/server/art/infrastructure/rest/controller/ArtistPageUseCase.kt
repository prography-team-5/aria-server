package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.CreateArtistInfoRequestDto
import com.aria.server.art.infrastructure.rest.dto.CreateSocialLinkRequestDto
import com.aria.server.art.infrastructure.rest.dto.CreateTagRequestDto
import com.aria.server.art.infrastructure.rest.dto.GetArtistPageResponseDto

interface ArtistPageUseCase {
    fun getArtistPage(artistId: Long): GetArtistPageResponseDto
    fun createArtistInfo(dto: CreateArtistInfoRequestDto)
    fun createTag(dto: CreateTagRequestDto)
    fun deleteTag(id: Long)
    fun createSocialLink(dto: CreateSocialLinkRequestDto)
    fun deleteSocialLink(id: Long)

}