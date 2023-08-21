package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.sociallink.SocialLink
import com.aria.server.art.domain.sociallink.SocialType

data class CreateSocialLinkRequestDto (
    val url: String,
    val socialType: SocialType
) {
    fun toEntity(artist: Member) =
        SocialLink(url, socialType, artist)
}

data class EditSocialLinkRequestDto (
    val socialLinkId: Long,
    val url: String
)

data class CreateSocialLinkResponseDto (
    val socialLinkId: Long
)