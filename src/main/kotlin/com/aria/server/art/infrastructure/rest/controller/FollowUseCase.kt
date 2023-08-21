package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.FollowResponseDto
import com.aria.server.art.infrastructure.rest.dto.GetFolloweeResponseDto
import com.aria.server.art.infrastructure.rest.dto.GetFollowerResponseDto
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface FollowUseCase {
    fun follow(followeeId: Long): FollowResponseDto
    fun unFollow(followeeId: Long)
    fun getFollowers(followeeId: Long, pageable: Pageable): Slice<GetFollowerResponseDto>
    fun getMyFollowers(pageable: Pageable): Slice<GetFollowerResponseDto>
    fun getFollowees(followerId: Long, pageable: Pageable): Slice<GetFolloweeResponseDto>
    fun getMyFollowees(pageable: Pageable): Slice<GetFolloweeResponseDto>
}