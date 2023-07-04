package com.aria.server.art.application.usecase

import com.aria.server.art.domain.art.Art


interface ArtService {
    fun createArt(art: Art): Art
    fun getRandomArt(): Art

    fun getArtsByArtistId(artistId: Long, page: Int, size: Int): List<Art>
}
