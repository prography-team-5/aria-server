package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.*

interface ArtistInfoDetailUseCase {
    fun getArtistInfoDetail(artistId: Long): GetArtistInfoDetailResponseDto
    fun getRandArtistInfoDetail(): GetArtistInfoDetailResponseDto
    fun createArtistInfo(dto: CreateArtistInfoRequestDto)
    fun createArtistTag(dto: CreateArtistTagRequestDto)
    fun deleteArtistTag(id: Long)
    fun createSocialLink(dto: CreateSocialLinkRequestDto)
    fun deleteSocialLink(id: Long)

}