package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.member.Role

data class GetMemberProfileResponseDto (
    val memberId: Long,
    val nickname: String,
    val profileImageUrl: String,
    val role: Role
) {
    companion object {
        fun from(member: Member): GetMemberProfileResponseDto =
            GetMemberProfileResponseDto(
                member.id,
                member.nickname,
                member.getProfileImageUrl(),
                member.role
            )
    }
}

data class EditNicknameRequestDto (
    val nickname: String,
)
