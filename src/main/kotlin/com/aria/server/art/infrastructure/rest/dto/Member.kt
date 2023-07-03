package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.follow.Follow
import com.aria.server.art.domain.member.Member

data class GetMemberProfileResponseDto (
    val memberId: Long,
    val nickname: String,
    val profileImageUrl: String
) {
    companion object {
        fun from(member: Member): GetMemberProfileResponseDto =
            GetMemberProfileResponseDto(
                member.id,
                member.nickname,
                member.profileImageUrl
            )
    }
}

data class EditNicknameRequestDto (
    val nickname: String,
)
