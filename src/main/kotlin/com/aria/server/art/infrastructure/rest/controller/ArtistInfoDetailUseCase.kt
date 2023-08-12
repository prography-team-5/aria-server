package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.*
import org.springframework.web.multipart.MultipartFile

interface ArtistInfoDetailUseCase {
    fun getArtistInfoDetail(artistId: Long): GetArtistInfoDetailResponseDto
    fun getMyArtistInfoDetail(): GetArtistInfoDetailResponseDto
    fun getRandArtistInfoDetail(): GetArtistInfoDetailResponseDto
    fun editArtistInfoIntro(dto: EditArtistInfoIntroRequestDto)
    fun createArtistTag(dto: CreateArtistTagRequestDto)
    fun deleteArtistTag(id: Long)
    fun createSocialLink(dto: CreateSocialLinkRequestDto)
    fun editSocialLink(id: Long, dto: EditSocialLinkRequestDto)
    fun deleteSocialLink(id: Long)
    fun changeProfileArtImageToNew(image: MultipartFile)
    fun changeProfileArtImageToBasic()

}
