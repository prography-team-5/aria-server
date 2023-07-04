package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.domain.art.Art


interface ArtService {
    fun createArt(art: Art): Art
    fun getRandomArt(): Art

    fun getArtById(id: Long): Art

    fun getArtList(): List<Art>
}
