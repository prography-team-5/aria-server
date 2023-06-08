package com.aria.server.art.application.service

import com.aria.server.art.application.usecase.FollowService
import com.aria.server.art.domain.exception.common.UnauthorizedException
import com.aria.server.art.domain.exception.follow.AlreadyFollowException
import com.aria.server.art.domain.exception.follow.FollowNotFoundException
import com.aria.server.art.domain.exception.follow.NoMoreFollowException
import com.aria.server.art.domain.follow.Follow
import com.aria.server.art.domain.member.Member
import com.aria.server.art.infrastructure.database.FollowRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class FollowServiceImpl(
    private val followRepository: FollowRepository
) : FollowService {

    override fun createFollow(follower: Member, followee: Member) {
        if (followRepository.existsByFollowerAndFollowee(follower, followee)) {
            throw AlreadyFollowException()
        } else {
            followRepository.save(Follow(follower, followee))
        }
    }

    override fun deleteFollow(follower: Member, followId: Long) =
        getFollowById(followId).run {
            if (this.follower == follower) {
                followRepository.delete(this)
            } else {
                throw UnauthorizedException()
            }
        }

    override fun getFollowsByFollower(follower: Member, pageable: Pageable): Slice<Follow> {
        val follows = followRepository.findFollowsByFollower(follower, pageable)
        if (follows.isEmpty) {
            throw NoMoreFollowException()
        } else {
            return follows
        }
    }

    override fun getFollowsByFollowee(followee: Member, pageable: Pageable): Slice<Follow> {
        val follows = followRepository.findFollowsByFollower(followee, pageable)
        if (follows.isEmpty) {
            throw NoMoreFollowException()
        } else {
            return follows
        }
    }

    private fun getFollowById(id: Long): Follow =
        followRepository.findByIdOrNull(id)
            ?: throw FollowNotFoundException()


}