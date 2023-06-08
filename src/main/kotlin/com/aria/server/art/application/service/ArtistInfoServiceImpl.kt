package com.aria.server.art.application.service

import com.aria.server.art.application.usecase.ArtistInfoService
import com.aria.server.art.domain.artistinfo.ArtistInfo
import com.aria.server.art.domain.exception.artistinfo.ArtistInfoAlreadyExistException
import com.aria.server.art.domain.exception.artistinfo.ArtistInfoNotFoundException
import com.aria.server.art.infrastructure.database.ArtistInfoRepository
import org.springframework.stereotype.Service

@Service
class ArtistInfoServiceImpl (
    private val artistInfoRepository: ArtistInfoRepository
): ArtistInfoService {

    override fun createArtistInfo(artistInfo: ArtistInfo) {
        if (artistInfoRepository.existsByMemberId(artistInfo.member.id)) {
            throw ArtistInfoAlreadyExistException()
        } else {
            artistInfoRepository.save(artistInfo)
        }
    }

    override fun updateArtistInfo() {
        TODO("Not yet implemented")
    }

    override fun getArtistInfo(artistId: Long): ArtistInfo =
        artistInfoRepository.findByMemberId(artistId)
            ?: throw ArtistInfoNotFoundException()


}