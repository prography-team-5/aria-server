package com.aria.server.art.infrastructure

import com.aria.server.art.infrastructure.dto.CreateArtRequest
import com.aria.server.art.infrastructure.dto.CreateArtResponse


interface IArtService {
    fun createArt(dto: CreateArtRequest): CreateArtResponse
}
