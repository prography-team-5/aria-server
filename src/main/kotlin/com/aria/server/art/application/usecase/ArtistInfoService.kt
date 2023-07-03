package com.aria.server.art.application.usecase

import com.aria.server.art.domain.artistinfo.ArtistInfo
import com.aria.server.art.domain.member.Member

interface ArtistInfoService {
    fun createArtistInfo(artistInfo: ArtistInfo)
    fun updateArtistInfo()
    fun getArtistInfo(artistId: Long): ArtistInfo
    fun getRandArtistInfo(): ArtistInfo
}