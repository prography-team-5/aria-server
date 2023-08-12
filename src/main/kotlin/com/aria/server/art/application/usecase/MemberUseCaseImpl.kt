package com.aria.server.art.application.usecase

import com.aria.server.art.domain.artistinfo.ArtistInfo
import com.aria.server.art.infrastructure.rest.controller.MemberUseCase
import com.aria.server.art.infrastructure.rest.dto.EditNicknameRequestDto
import com.aria.server.art.infrastructure.rest.dto.GetMemberProfileResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class MemberUseCaseImpl (
    private val memberService: MemberService,
    private val artistInfoService: ArtistInfoService,
    private val s3Service: S3Service
): MemberUseCase {

    val BASIC_MEMBER_PROFILE = "basic_member.jpg"

    @Transactional(readOnly = true)
    override fun getMemberProfile(id: Long): GetMemberProfileResponseDto {
        val member = memberService.getMemberById(id)
        return GetMemberProfileResponseDto.from(member)
    }

    @Transactional(readOnly = true)
    override fun getMyProfile(): GetMemberProfileResponseDto {
        val currentMember = memberService.getCurrentMember()
        return GetMemberProfileResponseDto.from(currentMember)
    }

    @Transactional
    override fun editNickname(dto: EditNicknameRequestDto) {
        val currentMember = memberService.getCurrentMember()
        memberService.checkDuplicateMemberByNickname(dto.nickname)
        currentMember.changeNickname(dto.nickname)
    }

    @Transactional
    override fun changeRoleToArtist(id: Long) {
        val member = memberService.getMemberById(id)
        val artistInfo = ArtistInfo(member)
        artistInfoService.createArtistInfo(artistInfo)
    }

    @Transactional
    override fun changeProfileImageToNew(image: MultipartFile) {
        val currentMember = memberService.getCurrentMember()
        val profileImageUrl = currentMember.getProfileImageUrl()
        if (profileImageUrl != BASIC_MEMBER_PROFILE) {
            s3Service.deleteImage(profileImageUrl)
        }
        val newProfileImageUrl = s3Service.uploadImage(image)
        currentMember.changeProfileImageUrl(newProfileImageUrl)
    }

    @Transactional
    override fun changeProfileImageToBasic() {
        val currentMember = memberService.getCurrentMember()
        currentMember.changeProfileImageUrl(BASIC_MEMBER_PROFILE)
    }
}