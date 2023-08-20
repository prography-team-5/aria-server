package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.artisttag.ArtistTag

data class CreateArtistTagRequestDto (
    val name: String
) {
    fun toEntity(artist: Member): ArtistTag =
        ArtistTag(name, artist)
}

data class CreateArtistTagResponseDto (
    val tagId: Long
)