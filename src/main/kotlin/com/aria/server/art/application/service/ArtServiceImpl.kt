package com.aria.server.art.application.service

import com.aria.server.art.domain.art.Art
import com.aria.server.art.infrastructure.database.ArtRepository
import com.aria.server.art.application.usecase.ArtService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArtServiceImpl(
    private val artRepository: ArtRepository
): ArtService {

    override fun createArt(art: Art): Art =
        artRepository.save(art)


    override fun getRandomArt(): Art =
        artRepository.findRandomArt()


    override fun getArtsByArtistId(artistId: Long, page: Int, size: Int): List<Art> =
        artRepository.findSliceByMemberId(artistId, PageRequest.of(page, size))
            .toList()
}
