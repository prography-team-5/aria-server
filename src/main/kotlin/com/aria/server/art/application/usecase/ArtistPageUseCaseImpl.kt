package com.aria.server.art.application.usecase

import com.aria.server.art.infrastructure.rest.controller.ArtistPageUseCase
import com.aria.server.art.infrastructure.rest.controller.MemberService
import com.aria.server.art.infrastructure.rest.dto.CreateArtistInfoRequestDto
import com.aria.server.art.infrastructure.rest.dto.CreateSocialLinkRequestDto
import com.aria.server.art.infrastructure.rest.dto.CreateTagRequestDto
import com.aria.server.art.infrastructure.rest.dto.GetArtistPageResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArtistPageUseCaseImpl (
    private val memberService: MemberService,
    private val followService: FollowService,
    private val artistInfoService: ArtistInfoService,
    private val socialLinkService: SocialLinkService,
    private val tagService: TagService,
): ArtistPageUseCase {

    @Transactional(readOnly = true)
    override fun getArtistPage(artistId: Long): GetArtistPageResponseDto {
        val artist = memberService.getMemberById(artistId)
        val artistInfo = artistInfoService.getArtistInfo(artistId)
        val tags = tagService.getTags(artistId)
        val followers = followService.getFollowerCount(artistId)
        val socialLinks = socialLinkService.getSocialLinks(artistId)
        return GetArtistPageResponseDto.from(artist, artistInfo, tags, followers, socialLinks)
    }

    @Transactional
    override fun createArtistInfo(dto: CreateArtistInfoRequestDto) {
        val artist = memberService.getCurrentMember()
        val artistInfo = dto.toEntity(artist)
        artistInfoService.createArtistInfo(artistInfo)
    }

    @Transactional
    override fun createTag(dto: CreateTagRequestDto) {
        val artist = memberService.getCurrentMember()
        val tag = dto.toEntity(artist)
        tagService.createTag(tag)
    }

    // TODO 본인검증
    @Transactional
    override fun deleteTag(id: Long) {
        tagService.deleteTag(id)
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