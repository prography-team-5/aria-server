package com.aria.server.art.config.security

import com.aria.server.art.domain.Member
import com.aria.server.art.domain.MemberRepository
import org.springframework.security.core.GrantedAuthority
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
    override fun loadUserByUsername(username: String): UserDetails =
        memberRepository.findByEmail(username)
            .map(::createUserDetails)
            .orElseThrow { UsernameNotFoundException("$username -> 데이터베이스에서 찾을 수 없습니다.") }

    private fun createUserDetails(member: Member): UserDetails =
        User(
            member.email,
            member.password, setOf(SimpleGrantedAuthority(member.role.toString()))
        )
}