package com.aria.server.art.application.usecase

import com.aria.server.art.infrastructure.rest.controller.ArtistInfoDetailUseCase
import com.aria.server.art.infrastructure.rest.dto.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArtistInfoDetailUseCaseImpl (
    private val memberService: MemberService,
    private val followService: FollowService,
    private val artistInfoService: ArtistInfoService,
    private val socialLinkService: SocialLinkService,
    private val artistTagService: ArtistTagService,
): ArtistInfoDetailUseCase {

    @Transactional(readOnly = true)
    override fun getArtistInfoDetail(artistId: Long): GetArtistInfoDetailResponseDto {
        val artist = memberService.getMemberById(artistId)
        val artistInfo = artistInfoService.getArtistInfo(artistId)
        val artistTags = artistTagService.getArtistTags(artistId)
        val followers = followService.getFollowerCount(artistId)
        val socialLinks = socialLinkService.getSocialLinks(artistId)
        return GetArtistInfoDetailResponseDto.from(artist, artistInfo, artistTags, followers, socialLinks)
    }

    @Transactional(readOnly = true)
    override fun getRandArtistInfoDetail(): GetArtistInfoDetailResponseDto {
        val artistInfo = artistInfoService.getRandArtistInfo()
        val artist = artistInfo.member
        val artistTags = artistTagService.getArtistTags(artist.id)
        val followers = followService.getFollowerCount(artist.id)
        val socialLinks = socialLinkService.getSocialLinks(artist.id)
        return GetArtistInfoDetailResponseDto.from(artist, artistInfo, artistTags, followers, socialLinks)
    }

    @Transactional
    override fun createArtistInfo(dto: CreateArtistInfoRequestDto) {
        val artist = memberService.getCurrentMember()
        val artistInfo = dto.toEntity(artist)
        artistInfoService.createArtistInfo(artistInfo)
    }

    @Transactional
    override fun createArtistTag(dto: CreateArtistTagRequestDto) {
        val artist = memberService.getCurrentMember()
        val artistTag = dto.toEntity(artist)
        artistTagService.createArtistTag(artistTag)
    }

    // TODO 본인검증
    @Transactional
    override fun deleteArtistTag(id: Long) {
        artistTagService.deleteArtistTag(id)
    }

    @Transactional
    override fun createSocialLink(dto: CreateSocialLinkRequestDto) {
        val artist = memberService.getCurrentMember()
        val socialLink = dto.toEntity(artist)
        socialLinkService.createSocialLink(socialLink)
    }

    @Transactional
    override fun deleteSocialLink(id: Long) {
        socialLinkService.deleteSocialLink(id)
    }

}