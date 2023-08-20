package com.aria.server.art.application.service

import com.aria.server.art.domain.art.Art
import com.aria.server.art.infrastructure.database.ArtRepository
import com.aria.server.art.application.usecase.ArtService
import com.aria.server.art.domain.exception.art.ArtNotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArtServiceImpl(
    private val artRepository: ArtRepository
): ArtService {

    override fun createArt(art: Art): Art =
        artRepository.save(art)


    override fun getRandomArts(count: Int): List<Art> =
        artRepository.findRandomArts(count)


    override fun getArtsByArtistId(artistId: Long, page: Int, count: Int): List<Art> =
        artRepository.findSliceByMemberId(artistId, PageRequest.of(page, count))
            .toList()

    override fun getArtById(artId: Long): Art =
        artRepository.findByIdOrNull(artId)
            ?: throw ArtNotFoundException()

    override fun searchArtsByTag(tag: String, page: Int, count: Int): List<Art> =
        artRepository.findArtsByTag(tag, PageRequest.of(page, count))

    override fun deleteArt(artId: Long) =
        artRepository.deleteById(artId)
}
