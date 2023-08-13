package com.aria.server.art.application.usecase

import com.aria.server.art.domain.artistinfo.ArtistInfo
import com.aria.server.art.domain.exception.common.AlreadyProfileImageException
import com.aria.server.art.domain.exception.member.AlreadyArtistException
import com.aria.server.art.domain.member.Role
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
        if (member.role == Role.ROLE_ARTIST) {
            throw AlreadyArtistException()
        } 
        member.changeRole(Role.ROLE_ARTIST)
        val artistInfo = ArtistInfo(member)
        artistInfoService.createArtistInfo(artistInfo)
    }

    @Transactional
    override fun changeProfileImage(image: MultipartFile) {
        val currentMember = memberService.getCurrentMember()
        if (!currentMember.isBasicProfileImage()) {
            val profileImageUrl = currentMember.getProfileImageUrl()
            s3Service.deleteImage(profileImageUrl)
        }
        val newProfileImageUrl = s3Service.uploadImage(image)
        currentMember.changeProfileImageUrl(newProfileImageUrl)
    }

    @Transactional
    override fun deleteProfileImage() {
        val currentMember = memberService.getCurrentMember()
        if (!currentMember.isBasicProfileImage()) {
            throw AlreadyProfileImageException()
        }
        val profileImageUrl = currentMember.getProfileImageUrl()
        s3Service.deleteImage(profileImageUrl)
        currentMember.deleteProfileImageUrl()
    }

    @Transactional
    override fun withdrawal() {
        val currentMemberId = memberService.getCurrentMember().id
        memberService.deleteMember(currentMemberId)
    }

}