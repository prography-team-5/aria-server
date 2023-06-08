package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.follow.Follow
import com.aria.server.art.domain.member.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface FollowRepository: JpaRepository<Follow, Long>{
    @EntityGraph(attributePaths = ["follower"])
    fun findFollowsByFollowee(followee: Member, pageable: Pageable): Slice<Follow>
    @EntityGraph(attributePaths = ["followee"])
    fun findFollowsByFollower(follower: Member, pageable: Pageable): Slice<Follow>
    fun existsByFollowerAndFollowee(follower: Member, followee: Member): Boolean
}