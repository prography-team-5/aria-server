package com.aria.server.art.application.usecase

import com.aria.server.art.domain.follow.Follow
import com.aria.server.art.domain.follow.TargetType
import com.aria.server.art.domain.member.Member
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface FollowService {
    fun createFollow(follower: Member, followee: Member): Follow
    fun deleteFollow(follower: Member, followee: Member)
    fun isFollowee(follower: Member, target: Member): Boolean
    fun getFollowsByFollowerId(id: Long, pageable: Pageable): Slice<Follow>
    fun getFollowsByFolloweeId(id: Long, pageable: Pageable): Slice<Follow>
    fun getFollowerCount(memberId: Long): Int
    fun getFolloweeCount(memberId: Long): Int

}