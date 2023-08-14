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
        } ?: throw ArtImageNotFoundException() // TODO: Make Transaction Error

        return CreateArtImageResponse(artImageId)
    }

    @Transactional(readOnly = true)
    override fun getRandomArts(count: Int): List<GetRandomArtResponseDto> =
        artService.getRandomArts(count).map { GetRandomArtResponseDto.from(it) }

    @Transactional(readOnly = true)
    override fun getArts(artistId: Long, page: Int, count: Int): List<SimpleArtDto> =
        artService.getArtsByArtistId(artistId, page, count)
            .map { SimpleArtDto.from(it) }

    @Transactional(readOnly = true)
    override fun getMyArts(page: Int, count: Int): List<SimpleArtDto> {
        val artist = memberService.getCurrentMember()
        return artService.getArtsByArtistId(artist.id, page, count)
            .map { SimpleArtDto.from(it) }
    }

    @Transactional(readOnly = true)
    override fun getArt(artId: Long): GetArtResponseDto {
        val art = artService.getArtById(artId)
        val artistInfo = artistInfoService.getArtistInfo(art.member.id)
        val socialLinks = socialLinkService.getSocialLinks(artistInfo.id)

        return GetArtResponseDto.from(art, artistInfo, socialLinks)
    }

    @Transactional
    override fun editArtImages(dto: EditArtImagesRequest) {
        val art = artService.getArtById(dto.artId)
        art.changeMainImage(artImageService.getArtImageById(dto.imageIds[0]))
        dto.imageIds.subList(1, dto.imageIds.size)
            .map { artImageService.getArtImageById(it) }

        val imagesToDelete = dto.originImageIds - dto.imageIds
        imagesToDelete
            .map {artImageService.getArtImageById(it)  }
            .map { s3Service.deleteImage(it.url) }
    }

    @Transactional
    override fun editArtTitle(dto: EditArtTitleRequest) =
        artService.getArtById(dto.artId)
            .run { changeTitle(dto.title) }

    @Transactional
    override fun editArtYear(dto: EditArtYearRequest) =
        artService.getArtById(dto.artId)
            .run { changeYear(dto.year) }


    @Transactional
    override fun editArtSize(dto: EditArtSizeRequest) =
        artService.getArtById(dto.artId)
            .run { changeSize(dto.size) }

    @Transactional
    override fun editArtStyle(dto: EditArtStyleRequest) =
        artService.getArtById(dto.artId)
            .run { changeStyle(dto.style) }

    // NOTE: 진영이랑 일단 태그들은 삭제 안 하기로 했음
    @Transactional
    override fun editArtTags(dto: EditArtTagsRequest) =
        artService.getArtById(dto.artId)
            .run { changeArtTags(dto.tags) }

    @Transactional
    override fun editArtDescription(dto: EditArtDescriptionRequest) =
        artService.getArtById(dto.artId)
            .run { changeDescription(dto.description) }
}
