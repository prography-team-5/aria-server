package com.aria.server.art.infrastructure.database

import com.aria.server.art.domain.follow.Follow
import com.aria.server.art.domain.member.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface FollowRepository: JpaRepository<Follow, Long>{

//    @EntityGraph(attributePaths = ["follower"])
//    fun findFollowsByFolloweeId(id: Long, pageable: Pageable): Slice<Follow>
//    @EntityGraph(attributePaths = ["followee"])
//    fun findFollowsByFollowerId(id: Long, pageable: Pageable): Slice<Follow>

    @EntityGraph(attributePaths = ["follower"])
    fun findFollowsByFolloweeId(id: Long): List<Follow>
    @EntityGraph(attributePaths = ["followee"])
    fun findFollowsByFollowerId(id: Long): List<Follow>
    fun existsByFollowerAndFollowee(follower: Member, followee: Member): Boolean
    fun findByFollowerAndFollowee(follower: Member, followee: Member): Follow?
    fun countByFolloweeId(id: Long): Int
    fun countByFollowerId(id: Long): Int
}