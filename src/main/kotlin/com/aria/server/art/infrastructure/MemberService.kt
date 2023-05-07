package com.aria.server.art.infrastructure

import com.aria.server.art.domain.Member
import com.aria.server.art.infrastructure.dto.*

interface MemberService {
    fun signUp(dto: SignUpRequestDto): TokenDto
    fun signIn(dto: SignInRequestDto): TokenDto
    fun getMemberInfo(email: String): Member
    fun getMemberInfo(id: Long): Member
}