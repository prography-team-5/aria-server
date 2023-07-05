package com.aria.server.art.application.usecase

import com.aria.server.art.infrastructure.rest.controller.MemberUseCase
import com.aria.server.art.infrastructure.rest.dto.EditNicknameRequestDto
import com.aria.server.art.infrastructure.rest.dto.GetMemberProfileResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberUseCaseImpl (
    private val memberService: MemberService
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

    // TODO 프로필 이미지 바꾸기
//    @Transactional
//    fun editMyProfileImage() {
//        val currentMember = memberService.getCurrentMember()
//        currentMember.changeNickname(dto.nickname)
//    }

}