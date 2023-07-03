package com.aria.server.art.application.usecase

import com.aria.server.art.domain.member.Member
import com.aria.server.art.infrastructure.rest.dto.SignInRequestDto
import com.aria.server.art.infrastructure.rest.dto.SignUpRequestDto
import com.aria.server.art.infrastructure.rest.dto.TokenDto

interface MemberService {
    fun createMember(member: Member)
    fun getCurrentMember(): Member
    fun getMemberById(id: Long): Member
    fun checkExistsMemberByEmail(email: String)
}
