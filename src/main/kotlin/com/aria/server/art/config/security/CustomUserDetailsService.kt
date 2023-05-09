package com.aria.server.art.config.security

import com.aria.server.art.domain.member.Member
import com.aria.server.art.domain.member.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

import javax.transaction.Transactional


@Service
class CustomUserDetailsService(private val memberRepository: MemberRepository): UserDetailsService {

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails =
        memberRepository.findByEmail(email)
            ?. let { createUserDetails(it) }
            ?: throw UsernameNotFoundException("$email -> 데이터베이스에서 찾을 수 없습니다.");


    private fun createUserDetails(member: Member): UserDetails =
        User(member.email, member.id.toString(), setOf(SimpleGrantedAuthority(member.role.toString())))
}