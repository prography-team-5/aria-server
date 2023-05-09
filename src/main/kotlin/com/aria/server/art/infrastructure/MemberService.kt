package com.aria.server.art.infrastructure

import com.aria.server.art.domain.member.FindType
import com.aria.server.art.domain.member.Member
import com.aria.server.art.infrastructure.dto.*

interface MemberService {
    fun signUp(dto: SignUpRequestDto): TokenDto
    fun signIn(dto: SignInRequestDto): TokenDto
    fun <T> getMember(param: T, findType: FindType): Member
    fun <T> checkDuplicatedMember(param: T, findType: FindType): Boolean

}