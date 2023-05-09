package com.aria.server.art.application

import com.aria.server.art.domain.art.ArtRepository
import com.aria.server.art.infrastructure.ArtService
import com.aria.server.art.infrastructure.dto.CreateArtRequest
import com.aria.server.art.infrastructure.dto.CreateArtResponse
import com.aria.server.art.infrastructure.dto.GetRandomArtResponse
import org.springframework.stereotype.Service

@Service
class ArtServiceImpl(
    private val artRepository: ArtRepository
): ArtService {
    override fun createArt(dto: CreateArtRequest): CreateArtResponse {
//        val art = Art(title = dto.title)
//        artRepository.save(art)
//        return CreateArtResponse(title = art.title)
        return CreateArtResponse(title = "test")
    }

    override fun getRandomArt(): GetRandomArtResponse =
        artRepository.findRandomArt()
            .run {
                GetRandomArtResponse(
                    artId = id,
                    memberId = member.id,
                    imageUrl = imageUrl,
                    title = title,
                    year = year,
                    styles = styles.map { it.name },
                    size = size,
                    description = description
                )
            }
}
