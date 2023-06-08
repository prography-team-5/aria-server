package com.aria.server.art.infrastructure.rest.dto

import com.aria.server.art.domain.follow.Follow

data class GetFollowerResponseDto(
    val followId: Long,
    val followerId: Long,
    val followerNickname: String,
    val followerProfileImageUrl: String
) {
    companion object {
        fun from(follow: Follow): GetFollowerResponseDto =
            GetFollowerResponseDto(
                follow.id,
                follow.follower.id,
                follow.follower.nickname,
                follow.follower.profileImageUrl
            )
    }
}

data class GetFolloweeResponseDto(
    val followId: Long,
    val followeeId: Long,
    val followeeNickname: String,
    val followeeProfileImageUrl: String
) {
    companion object {
        fun from(follow: Follow): GetFolloweeResponseDto =
            GetFolloweeResponseDto(
                follow.id,
                follow.followee.id,
                follow.followee.nickname,
                follow.followee.profileImageUrl
            )
    }
}