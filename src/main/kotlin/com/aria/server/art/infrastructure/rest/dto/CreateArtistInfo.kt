package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.artistinfo.ArtistInfo
import com.aria.server.art.domain.member.Member


data class CreateArtistInfoRequestDto (
    val profileArtImageUrl: String?,
    val intro: String?
) {
    fun toEntity(artist: Member) =
        ArtistInfo(artist, profileArtImageUrl, intro)

}