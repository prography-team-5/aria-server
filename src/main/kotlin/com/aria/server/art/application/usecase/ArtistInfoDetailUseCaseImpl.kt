package com.aria.server.art.application.usecase

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

    val BASIC_PROFILE_ART = "basic_art.jpg"

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
    override fun changeProfileArtImageToNew(image: MultipartFile) {
        val currentMember = memberService.getCurrentMember()
        val profileImageUrl = currentMember.getProfileImageUrl()
        if (profileImageUrl != BASIC_PROFILE_ART) {
            s3Service.deleteImage(profileImageUrl)
        }
        val newProfileImageUrl = s3Service.uploadImage(image)
        currentMember.changeProfileImageUrl(newProfileImageUrl)
    }

    @Transactional
    override fun changeProfileArtImageToBasic() {
        val currentMember = memberService.getCurrentMember()
        currentMember.changeProfileImageUrl(BASIC_PROFILE_ART)
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
    override fun editSocialLink(id: Long, dto: EditSocialLinkRequestDto) {
        socialLinkService.updateSocialLink(id, dto.url)
    }

    @Transactional
    override fun deleteSocialLink(id: Long) {
        socialLinkService.deleteSocialLink(id)
    }

}