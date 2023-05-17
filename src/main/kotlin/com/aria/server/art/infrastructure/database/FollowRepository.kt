package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.follow.Follow
import com.aria.server.art.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface FollowRepository: JpaRepository<Follow, Long>{
    // TODO 프론트와 얘기 후 Paging 도입여부 결정
    fun findFollowsByFollowee(followee: Member): List<Follow>
}