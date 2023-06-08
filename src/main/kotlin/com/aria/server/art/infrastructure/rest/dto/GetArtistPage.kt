package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.artistinfo.ArtistInfo
import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.sociallink.SocialLink
import com.aria.server.art.domain.sociallink.SocialType
import com.aria.server.art.domain.tag.Tag

data class GetArtistPageResponseDto (
    val profileArtImageUrl: String?,
    val artistProfile: ArtistProfileResponseDto,
    val tags: List<TagResponseDto>,
    val followers: Int,
    val intro: String?,
    val socialLinks: List<SocialLinkResponseDto>
) {
    companion object {
        fun from(
            artistProfile: Member,
            artistInfo: ArtistInfo,
            tags: MutableList<Tag>,
            followers: Int,
            socialLinks: MutableList<SocialLink>
            ): GetArtistPageResponseDto =
            GetArtistPageResponseDto(
                artistInfo.profileArtImageUrl,
                ArtistProfileResponseDto.from(artistProfile),
                tags.map { tag -> TagResponseDto.from(tag) },
                followers,
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

data class TagResponseDto (
    val tagId: Long,
    val name: String
) {
    companion object {
        fun from(tag: Tag): TagResponseDto =
            TagResponseDto(
                tag.id,
                tag.name
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