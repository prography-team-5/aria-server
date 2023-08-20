package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.art.Art
import com.aria.server.art.domain.artistinfo.ArtistInfo
import com.aria.server.art.domain.sociallink.SocialLink

data class GetArtResponseDto(
    val artistNickname: String,
    val artistProfileImageUrl: String,
    val imagesUrl: List<String>,
    val title: String,
    val description: String,
    val year: Int,
    val size: SizeDto,
    val style: String,
    val artTags: List<String>,
    val artistSocialLinks: List<SocialLinkDto>
){
    companion object {
        fun from(art: Art, artistInfo: ArtistInfo, socialLinks: List<SocialLink>): GetArtResponseDto =
            GetArtResponseDto(
                artistNickname = art.member.nickname,
                artistProfileImageUrl = artistInfo.getProfileArtImageUrl(),
                imagesUrl = listOf(art.mainImage.getUrl()) + art.images.map { it.getUrl() },
                title = art.title,
                description = art.description,
                year = art.year,
                size = SizeDto(art.size.width, art.size.height),
                style = art.style,
                artTags = art.artTags.map { it.name },
                artistSocialLinks = socialLinks.map { SocialLinkDto.from(it) }
            )
    }
}

data class SocialLinkDto(
    val url: String,
    val socialType: String
) {
    companion object {
        fun from(socialLink: SocialLink): SocialLinkDto =
            SocialLinkDto(
                url = socialLink.url,
                socialType = socialLink.socialType.name
            )
    }
}