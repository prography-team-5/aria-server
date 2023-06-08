package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.tag.Tag

data class CreateTagRequestDto (
    val name: String
) {
    fun toEntity(artist: Member): Tag =
        Tag(name, artist)
}