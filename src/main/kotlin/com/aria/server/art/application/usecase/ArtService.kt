package com.aria.server.art.application.usecase

import com.aria.server.art.domain.art.Art


interface ArtService {
    fun createArt(art: Art): Art
    fun getRandomArts(size: Int): List<Art>
    fun getArtsByArtistId(artistId: Long, page: Int, size: Int): List<Art>
    fun getArtById(artId: Long): Art
}
