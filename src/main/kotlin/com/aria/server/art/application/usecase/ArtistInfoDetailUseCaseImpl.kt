package com.aria.server.art.application.usecase

import com.aria.server.art.domain.exception.common.AlreadyProfileImageException
import com.aria.server.art.infrastructure.rest.controller.ArtistInfoDetailUseCase
import com.aria.server.art.infrastructure.rest.dto.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class ArtistInfoDetailUseCaseImpl (
    private val memberService: MemberService,
    private val followService: FollowService,
    private val artistInfoService: ArtistInfoService,
    private val socialLinkService: SocialLinkService,
    private val artistTagService: ArtistTagService,
    private val s3Service: S3Service
): ArtistInfoDetailUseCase {

    @Transactional(readOnly = true)
    override fun getArtistInfoDetail(artistId: Long): GetArtistInfoDetailResponseDto {
        val currentMember = memberService.getCurrentMember()
        val artist = memberService.getMemberById(artistId)
        val isFollowee = followService.isFollowee(currentMember, artist)
        val artistInfo = artistInfoService.getArtistInfo(artistId)
        val artistTags = artistTagService.getArtistTags(artistId)
        val followers = followService.getFollowerCount(artistId)
        val socialLinks = socialLinkService.getSocialLinks(artistId)
        return GetArtistInfoDetailResponseDto.from(artist, artistInfo, artistTags, followers, isFollowee, socialLinks)
    }

    @Transactional(readOnly = true)
    override fun getMyArtistInfoDetail(): GetArtistInfoDetailResponseDto {
        val artist = memberService.getCurrentMember()
        val artistInfo = artistInfoService.getArtistInfo(artist.id)
        val artistTags = artistTagService.getArtistTags(artist.id)
        val followers = followService.getFollowerCount(artist.id)
        val socialLinks = socialLinkService.getSocialLinks(artist.id)
        return GetArtistInfoDetailResponseDto.from(artist, artistInfo, artistTags, followers, null, socialLinks)
    }

    @Transactional(readOnly = true)
    override fun getRandArtistInfoDetail(): GetArtistInfoDetailResponseDto {
        val artistInfo = artistInfoService.getRandArtistInfo()
        val currentMember = memberService.getCurrentMember()
        val artist = artistInfo.member
        val isFollowee = followService.isFollowee(currentMember, artist)
        val artistTags = artistTagService.getArtistTags(artist.id)
        val followers = followService.getFollowerCount(artist.id)
        val socialLinks = socialLinkService.getSocialLinks(artist.id)
        return GetArtistInfoDetailResponseDto.from(artist, artistInfo, artistTags, followers, isFollowee, socialLinks)
    }

    @Transactional
    override fun editArtistInfoIntro(dto: EditArtistInfoIntroRequestDto) {
        val artist = memberService.getCurrentMember()
        val artistInfo = artistInfoService.getArtistInfo(artist.id)
        artistInfo.changeIntro(dto.intro)
    }

    @Transactional
    override fun changeProfileArtImage(image: MultipartFile) {
        val currentMemberId = memberService.getCurrentMember().id
        val artistInfo = artistInfoService.getArtistInfo(currentMemberId)
        if (!artistInfo.isBasicProfileArtImage()) {
            val profileArtImageUrl = artistInfo.getProfileArtImageUrl()
            s3Service.deleteImage(profileArtImageUrl)
        }
        val newProfileArtImageUrl = s3Service.uploadImage(image)
        artistInfo.changeProfileArtImageUrl(newProfileArtImageUrl)
    }

    @Transactional
    override fun deleteProfileArtImage() {
        val currentMemberId = memberService.getCurrentMember().id
        val artistInfo = artistInfoService.getArtistInfo(currentMemberId)
        if (!artistInfo.isBasicProfileArtImage()) {
            throw AlreadyProfileImageException()
        }
        val profileArtImageUrl = artistInfo.getProfileArtImageUrl()
        s3Service.deleteImage(profileArtImageUrl)
        artistInfo.deleteProfileArtImageUrl()
    }

    @Transactional
    override fun createArtistTag(dto: CreateArtistTagRequestDto): CreateArtistTagResponseDto {
        val artist = memberService.getCurrentMember()
        val artistTag = dto.toEntity(artist)
        val artistTagId = artistTagService.createArtistTag(artistTag).id
        return CreateArtistTagResponseDto(artistTagId)
    }

    // TODO 본인검증
    @Transactional
    override fun deleteArtistTag(id: Long) {
        artistTagService.deleteArtistTag(id)
    }

    @Transactional
    override fun createSocialLink(dto: CreateSocialLinkRequestDto): CreateSocialLinkResponseDto {
        val artist = memberService.getCurrentMember()
        val socialLink = dto.toEntity(artist)
        val socialLinkId = socialLinkService.createSocialLink(socialLink).id
        return CreateSocialLinkResponseDto(socialLinkId)
    }

    @Transactional
    override fun editSocialLink(dto: EditSocialLinkRequestDto) {
        socialLinkService.updateSocialLink(dto.socialLinkId, dto.url)
    }

    @Transactional
    override fun deleteSocialLink(id: Long) {
        socialLinkService.deleteSocialLink(id)
    }

}