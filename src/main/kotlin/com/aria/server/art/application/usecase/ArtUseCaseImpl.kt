package com.aria.server.art.application.usecase

import com.aria.server.art.domain.art.Art
import com.aria.server.art.domain.art.ArtImage
import com.aria.server.art.domain.art.Size
import com.aria.server.art.domain.art.Style
import com.aria.server.art.infrastructure.rest.controller.ArtUseCase
import com.aria.server.art.infrastructure.rest.dto.CreateArtImageResponse
import com.aria.server.art.infrastructure.rest.dto.CreateArtRequest
import com.aria.server.art.infrastructure.rest.dto.CreateArtResponse
import com.aria.server.art.infrastructure.rest.dto.GetRandomArtResponse
import com.aria.server.art.infrastructure.rest.dto.SimpleArtDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class ArtUseCaseImpl(
    private val artService: ArtService,
    private val memberService: MemberService,
    private val artImageService: ArtImageService,
    private val s3Service: S3Service
):ArtUseCase {

    @Transactional
    override fun createArt(dto: CreateArtRequest): CreateArtResponse {
        val artist = memberService.getCurrentMember()
        val styles = dto.styles.map { Style(it) }
        val artImages = dto.artImageIds.map { artImageService.getArtImageById(it) }
        val mainArtImage = artImageService.getArtImageById(dto.artImageIds[0])

        val art = Art(
            title = dto.title,
            year = dto.year,
            styles = styles.toMutableList(),
            size = Size(dto.size.width, dto.size.height),
            description = dto.description,
            mainImage = mainArtImage,
            images = artImages.toMutableList(),
            member = artist
        )

        styles.map { it.changeArt(art) }
        artImages.map { it.changeArt(art) }
        artService.createArt(art)

        // TODO: 삭제된 이미지는 S3에서 이미지 삭제
        // val removedImageUrls = dto.totalImageUrlIds - dto.imageUrlIds
        return CreateArtResponse(art.id)
    }

    override fun createArtImage(image: MultipartFile): CreateArtImageResponse {
        val artist = memberService.getCurrentMember()
        val imageUrl = s3Service.uploadImage(image)
        val artImageId = artImageService.createArtImage(ArtImage(imageUrl, artist)).id

        return CreateArtImageResponse(artImageId)
    }


    @Transactional(readOnly = true)
    override fun getRandomArt(): GetRandomArtResponse =
        artService.getRandomArt()
            .run {
                GetRandomArtResponse(
                    artId = id,
                    memberId = member.id,
                    mainImageUrl = mainImage.url,
                    title = title,
                    year = year,
                    styles = styles.map { it.name },
                    size = size,
                    description = description
                )
            }

    override fun getArts(artistId: Long, page: Int, size: Int): List<SimpleArtDto> =
        artService.getArtsByArtistId(artistId, page, size)
            .map { SimpleArtDto.from(it) }

    override fun getMyArts(page: Int, size: Int): List<SimpleArtDto> {
        val artist = memberService.getCurrentMember()
        return artService.getArtsByArtistId(artist.id, page, size)
            .map { SimpleArtDto.from(it) }
    }
}
