package com.aria.server.art.application.service

import com.aria.server.art.domain.exception.member.CurrentMemberNotFoundException
import com.aria.server.art.domain.exception.member.DuplicatedNicknameException
import com.aria.server.art.domain.exception.member.MemberNotFoundException
import com.aria.server.art.domain.member.Member
import com.aria.server.art.infrastructure.database.MemberRepository
import com.aria.server.art.application.usecase.MemberService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl (
    private val memberRepository: MemberRepository
): MemberService {

    override fun createMember(member: Member) {
        checkDuplicateMemberByNickname(member.nickname)
        memberRepository.save(member)
    }

    override fun getCurrentMember() =
        try {
            getMemberByEmail(SecurityContextHolder.getContext().authentication.name)
        }  catch (e: MemberNotFoundException) {
            throw CurrentMemberNotFoundException()
        }

    override fun getMemberById(id: Long) =
        memberRepository.findByIdOrNull(id)
            ?:throw MemberNotFoundException("ID")

    fun getMemberByEmail(email: String) =
        memberRepository.findByEmail(email)
            ?:throw MemberNotFoundException("이메일")

    fun getMemberByNickname(nickname: String) =
        memberRepository.findByNickname(nickname)
            ?:throw MemberNotFoundException("닉네임")

    override fun checkDuplicateMemberByNickname(nickname: String) {
        if (memberRepository.existsByNickname(nickname))
            throw DuplicatedNicknameException()
    }

    override fun checkExistsMemberByEmail(email: String) {
        if (!memberRepository.existsByEmail(email))
            throw MemberNotFoundException("이메일")
    }


}