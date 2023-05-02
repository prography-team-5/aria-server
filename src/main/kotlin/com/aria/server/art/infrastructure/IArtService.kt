package com.aria.server.art.infrastructure

import com.aria.server.art.infrastructure.dto.CreateArtRequest
import com.aria.server.art.infrastructure.dto.CreateArtResponse
import com.aria.server.art.infrastructure.dto.GetRandomArtResponse


interface IArtService {
    fun createArt(dto: CreateArtRequest): CreateArtResponse
    fun getRandomArt(): GetRandomArtResponse
}
