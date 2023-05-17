package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.art.Size

data class FollowerDto(
    val followId: Long,
    val followerId: Long,
    val followerNickname: String,
    val followerProfileImageUrl: String
)

data class FolloweeDto(
    val followId: Long,
    val followeeId: Long,
    val followeeNickname: String,
    val followeeProfileImageUrl: String
)