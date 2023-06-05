package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.domain.member.Member
import com.aria.server.art.infrastructure.rest.dto.GetFolloweeResponseDto
import com.aria.server.art.infrastructure.rest.dto.GetFollowerResponseDto

interface FollowService {
    fun follow(follower: Member, followee: Member)
    fun unfollow(follower: Member, followId: Long)
    fun getFollowers(followee: Member): List<GetFollowerResponseDto>
    fun getFollowees(follower: Member): List<GetFolloweeResponseDto>
}