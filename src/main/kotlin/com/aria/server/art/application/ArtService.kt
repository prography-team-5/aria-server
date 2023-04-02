package com.aria.server.art.application

import com.aria.server.art.domain.Art
import com.aria.server.art.domain.ArtRepository
import com.aria.server.art.infrastructure.IArtService
import com.aria.server.art.infrastructure.dto.CreateArtRequest
import com.aria.server.art.infrastructure.dto.CreateArtResponse
import org.springframework.stereotype.Service

@Service
class ArtService(
    private val artRepository: ArtRepository
): IArtService {
    override fun createArt(dto: CreateArtRequest): CreateArtResponse {
        val art = Art(title = dto.title)
        artRepository.save(art)

        return CreateArtResponse(title = art.title)
    }
}
