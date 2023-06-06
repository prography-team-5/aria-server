package com.aria.server.art.application.usecase

import com.aria.server.art.domain.follow.Follow
import com.aria.server.art.domain.follow.TargetType
import com.aria.server.art.domain.member.Member
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface FollowService {
    fun createFollow(follower: Member, followee: Member)
    fun deleteFollow(follower: Member, followId: Long)
    fun getFollows(target: Member, targetType: TargetType, pageable: Pageable): Slice<Follow>
}