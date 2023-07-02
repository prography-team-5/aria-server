package com.aria.server.art.application.service

import com.aria.server.art.domain.art.Art
import com.aria.server.art.infrastructure.database.ArtRepository
import com.aria.server.art.infrastructure.rest.controller.ArtService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArtServiceImpl(
    private val artRepository: ArtRepository
): ArtService {

    @Transactional
    override fun createArt(art: Art): Art =
        artRepository.save(art)


    @Transactional(readOnly = true)
    override fun getRandomArt(): Art =
        artRepository.findRandomArt()
}
