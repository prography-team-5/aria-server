package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?
    fun findByNickname(nickname: String): Member?
    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}