package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.domain.member.Member
import com.aria.server.art.infrastructure.rest.dto.FolloweeDto
import com.aria.server.art.infrastructure.rest.dto.FollowerDto

interface FollowService {
    fun follow(follower: Member, followee: Member)
    fun unfollow(follower: Member, followId: Long)
    fun getFollowers(followee: Member): List<FollowerDto>
    fun getFollowees(follower: Member): List<FolloweeDto>
}