package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.CreateArtRequest
import com.aria.server.art.infrastructure.rest.dto.CreateArtResponse
import com.aria.server.art.infrastructure.rest.dto.GetRandomArtResponse


interface ArtService {
    fun createArt(dto: CreateArtRequest): CreateArtResponse
    fun getRandomArt(): GetRandomArtResponse
}
