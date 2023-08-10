package com.aria.server.art.application.usecase

import com.aria.server.art.domain.art.Art
import com.aria.server.art.domain.art.ArtImage
import com.aria.server.art.domain.art.Size
import com.aria.server.art.domain.art.ArtTag
import com.aria.server.art.domain.exception.art.ArtImageNotFoundException
import com.aria.server.art.infrastructure.rest.controller.ArtUseCase
import com.aria.server.art.infrastructure.rest.dto.CreateArtImageResponse
import com.aria.server.art.infrastructure.rest.dto.CreateArtRequest
import com.aria.server.art.infrastructure.rest.dto.CreateArtResponse
import com.aria.server.art.infrastructure.rest.dto.GetArtResponseDto
import com.aria.server.art.infrastructure.rest.dto.GetRandomArtResponse
import com.aria.server.art.infrastructure.rest.dto.SimpleArtDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.multipart.MultipartFile

@Service
class ArtUseCaseImpl(
    private val artService: ArtService,
    private val artistInfoService: ArtistInfoService,
    private val socialLinkService: SocialLinkService,
    private val memberService: MemberService,
    private val artImageService: ArtImageService,
    private val s3Service: S3Service,
    private val transactionTemplate: TransactionTemplate
):ArtUseCase {

    @Transactional
    override fun createArt(dto: CreateArtRequest): CreateArtResponse {
        val artist = memberService.getCurrentMember()
        val artTags = dto.artTags.map { ArtTag(it) }
        val artImages = dto.artImageIds.map { artImageService.getArtImageById(it) }
        val mainArtImage = artImageService.getArtImageById(dto.artImageIds[0])

        val art = Art(
            title = dto.title,
            year = dto.year,
            style = dto.style,
            tags = artTags.toMutableList(),
            size = Size(dto.size.width, dto.size.height),
            description = dto.description,
            mainImage = mainArtImage,
            images = artImages.toMutableList(),
            member = artist
        )

        artTags.map { it.changeArt(art) }
        artImages.map { it.changeArt(art) }
        artService.createArt(art)

        // TODO: 삭제된 이미지는 S3에서 이미지 삭제
        // val removedImageUrls = dto.totalImageUrlIds - dto.imageUrlIds
        return CreateArtResponse(art.id)
    }

    override fun createArtImage(image: MultipartFile): CreateArtImageResponse {
        val imageUrl = s3Service.uploadImage(image)

        val artImageId = transactionTemplate.execute {
            val artist = memberService.getCurrentMember()
            artImageService.createArtImage(ArtImage(imageUrl, artist)).id
        } ?: throw ArtImageNotFoundException() // TODO: Make Transcation Error

        return CreateArtImageResponse(artImageId)
    }

    @Transactional(readOnly = true)
    override fun getRandomArt(): GetRandomArtResponse =
        artService.getRandomArt()
            .let { GetRandomArtResponse.from(it) }

    @Transactional(readOnly = true)
    override fun getArts(artistId: Long, page: Int, size: Int): List<SimpleArtDto> =
        artService.getArtsByArtistId(artistId, page, size)
            .map { SimpleArtDto.from(it) }

    @Transactional(readOnly = true)
    override fun getMyArts(page: Int, size: Int): List<SimpleArtDto> {
        val artist = memberService.getCurrentMember()
        return artService.getArtsByArtistId(artist.id, page, size)
            .map { SimpleArtDto.from(it) }
    }

    @Transactional(readOnly = true)
    override fun getArt(artId: Long): GetArtResponseDto {
        val art = artService.getArtById(artId)
        val artistInfo = artistInfoService.getArtistInfo(art.member.id)
        val socialLinks = socialLinkService.getSocialLinks(artistInfo.id)

        return GetArtResponseDto.from(art, artistInfo, socialLinks)
    }
}
