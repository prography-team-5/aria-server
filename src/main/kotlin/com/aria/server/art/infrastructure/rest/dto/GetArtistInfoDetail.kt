package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.artistinfo.ArtistInfo
import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.sociallink.SocialLink
import com.aria.server.art.domain.sociallink.SocialType
import com.aria.server.art.domain.artisttag.ArtistTag

data class GetArtistInfoDetailResponseDto (
    val artistProfile: ArtistProfileResponseDto,
    val artistTags: List<ArtistTagResponseDto>,
    val followerCount: Int,
    val isFollowee: Boolean?,
    val intro: String?,
    val socialLinks: List<SocialLinkResponseDto>
) {
    companion object {
        fun from(
            artistProfile: Member,
            artistInfo: ArtistInfo,
            artistTags: MutableList<ArtistTag>,
            followerCount: Int,
            isFollowee: Boolean?,
            socialLinks: MutableList<SocialLink>
            ): GetArtistInfoDetailResponseDto =
            GetArtistInfoDetailResponseDto(
                ArtistProfileResponseDto.from(artistProfile),
                artistTags.map { tag -> ArtistTagResponseDto.from(tag) },
                followerCount,
                isFollowee,
                artistInfo.intro,
                socialLinks.map { socialLink -> SocialLinkResponseDto.from(socialLink) }
            )
    }
}

data class SocialLinkResponseDto (
    val socialLinkId: Long,
    val socialType: SocialType,
    val url: String
) {
    companion object {
        fun from(socialLink: SocialLink): SocialLinkResponseDto =
            SocialLinkResponseDto(
                socialLink.id,
                socialLink.socialType,
                socialLink.url
            )
    }
}

data class ArtistTagResponseDto (
    val artistTagId: Long,
    val name: String
) {
    companion object {
        fun from(artistTag: ArtistTag): ArtistTagResponseDto =
            ArtistTagResponseDto(
                artistTag.id,
                artistTag.name
            )
    }
}

data class ArtistProfileResponseDto (
    val memberId: Long,
    val nickname: String,
    val profileImageUrl: String
) {
    companion object {
        fun from(member: Member): ArtistProfileResponseDto =
            ArtistProfileResponseDto(
                member.id,
                member.nickname,
                member.profileImageUrl
            )
    }
}