package com.aria.server.art.application

import com.aria.server.art.domain.exception.common.UnauthorizedException
import com.aria.server.art.domain.exception.follow.FollowNotFoundException
import com.aria.server.art.domain.follow.Follow
import com.aria.server.art.domain.member.Member
import com.aria.server.art.infrastructure.database.FollowRepository
import com.aria.server.art.infrastructure.rest.controller.FollowService
import com.aria.server.art.infrastructure.rest.dto.FolloweeDto
import com.aria.server.art.infrastructure.rest.dto.FollowerDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FollowServiceImpl (
    private val followRepository: FollowRepository
): FollowService {

    @Transactional
    override fun follow(follower: Member, followee: Member) {
        followRepository.save(Follow(follower, followee))
    }

    @Transactional
    override fun unfollow(follower: Member, followId: Long) =
        getFollowById(followId).run {
            if (this.follower == follower) {
                followRepository.delete(this)
            } else {
                throw UnauthorizedException()
            }
        }

    // TODO fetch join 이 필요할까?
    @Transactional(readOnly = true)
    override fun getFollowers(followee: Member) =
        followRepository.findFollowsByFollowee(followee)
            .map { follow ->
                FollowerDto(
                    follow.id,
                    follow.follower.id,
                    follow.follower.nickname,
                    follow.follower.profileImageUrl)
            }

    @Transactional(readOnly = true)
    override fun getFollowees(follower: Member) =
        followRepository.findFollowsByFollowee(follower)
            .map { follow ->
                FolloweeDto(
                    follow.id,
                    follow.followee.id,
                    follow.followee.nickname,
                    follow.followee.profileImageUrl)
            }

    private fun getFollowById(id: Long): Follow =
        followRepository.findByIdOrNull(id)
            ?:throw FollowNotFoundException()

}