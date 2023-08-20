package com.aria.server.art.application.usecase

import com.aria.server.art.domain.art.Art


interface ArtService {
    fun createArt(art: Art): Art
    fun getRandomArts(count: Int): List<Art>
    fun getArtsByArtistId(artistId: Long, page: Int, count: Int): List<Art>
    fun getArtById(artId: Long): Art
    fun searchArtsByTag(tag: String, page: Int, count: Int): List<Art>
}
