package com.aria.server.art.application

import com.amazonaws.AmazonServiceException
import com.amazonaws.SdkClientException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.aria.server.art.domain.art.Art
import com.aria.server.art.domain.art.ArtImage
import com.aria.server.art.domain.art.Size
import com.aria.server.art.domain.art.Style
import com.aria.server.art.domain.exception.art.ArtImageS3Exception
import com.aria.server.art.domain.exception.art.ArtImageNotFoundException
import com.aria.server.art.domain.member.Member
import com.aria.server.art.infrastructure.database.ArtImageRepository
import com.aria.server.art.infrastructure.database.ArtRepository
import com.aria.server.art.infrastructure.rest.controller.ArtService
import com.aria.server.art.infrastructure.rest.dto.CreateArtImageResponse
import com.aria.server.art.infrastructure.rest.dto.CreateArtRequest
import com.aria.server.art.infrastructure.rest.dto.CreateArtResponse
import com.aria.server.art.infrastructure.rest.dto.GetRandomArtResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ArtServiceImpl(
    @Value("\${cloud.aws.s3.bucket}") private val bucketName: String,
    private val artRepository: ArtRepository,
    private val artImageRepository: ArtImageRepository,
    private val s3Client: AmazonS3,
    private val transactionTemplate: TransactionTemplate
): ArtService {

    @Transactional
    override fun createArt(dto: CreateArtRequest): CreateArtResponse {
        val member = SecurityContextHolder.getContext().authentication.principal as Member

        val styles = dto.styles.map { Style(it) }
        val artImages = dto.artImageIds.map { artImageRepository.findById(it).orElseThrow() }
        val art = Art(
            title = dto.title,
            year = dto.year,
            styles = styles.toMutableList(),
            size = Size(dto.size.width, dto.size.height),
            description = dto.description,
            mainImage = artImageRepository.findById(dto.artImageIds[0]).orElseThrow { ArtImageNotFoundException() },
            images = artImages.toMutableList(),
            member = member
        )

        styles.map { it.changeArt(art) }
        artImages.map { it.changeArt(art) }
        artRepository.save(art)
        // TODO: 삭제된 이미지는 S3에서 이미지 삭제
        // val removedImageUrls = dto.totalImageUrlIds - dto.imageUrlIds
        return CreateArtResponse(art.id)
    }

    override fun createArtImage(image: MultipartFile): CreateArtImageResponse {
        val member = SecurityContextHolder.getContext().authentication.principal as Member

        val objectKey = UUID.randomUUID().toString()
        val metadata = ObjectMetadata().apply {
            contentType = image.contentType
            contentLength = image.size
        }
        try {
            s3Client.putObject(bucketName, objectKey, image.inputStream, metadata)
        } catch (e: SdkClientException) {
            throw ArtImageS3Exception()
        } catch (e: AmazonServiceException) {
            throw ArtImageS3Exception()
        }

        val imageUrl = s3Client.getUrl(bucketName, objectKey).toString()
        val artImageUrlId = transactionTemplate.execute {
                artImageRepository.save(ArtImage(imageUrl, member))
        } ?.id ?: throw ArtImageNotFoundException()

        return CreateArtImageResponse(artImageUrlId)
    }

    @Transactional(readOnly = true)
    override fun getRandomArt(): GetRandomArtResponse =
        artRepository.findRandomArt()
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
}
