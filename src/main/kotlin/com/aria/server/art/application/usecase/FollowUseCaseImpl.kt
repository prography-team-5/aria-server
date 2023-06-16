package com.aria.server.art.application.usecase

import com.aria.server.art.infrastructure.rest.controller.FollowUseCase
import com.aria.server.art.infrastructure.rest.controller.MemberService
import com.aria.server.art.infrastructure.rest.dto.GetFolloweeResponseDto
import com.aria.server.art.infrastructure.rest.dto.GetFollowerResponseDto
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FollowUseCaseImpl (
    private val memberService: MemberService,
    private val followService: FollowService
): FollowUseCase {

    @Transactional
    override fun follow(followeeId: Long) {
        val follower = memberService.getCurrentMember()
        val followee = memberService.getMemberById(followeeId)
        followService.createFollow(follower, followee)
    }

    @Transactional
    override fun unFollow(followId: Long) {
        val follower = memberService.getCurrentMember()
        followService.deleteFollow(follower, followId)
    }

    @Transactional(readOnly = true)
    override fun getFollowers(followeeId: Long, pageable: Pageable): Slice<GetFollowerResponseDto> =
        followService.getFollowsByFolloweeId(followeeId, pageable)
            .map { follow ->
                GetFollowerResponseDto.from(follow)
            }


    @Transactional(readOnly = true)
    override fun getFollowees(followerId: Long, pageable: Pageable): Slice<GetFolloweeResponseDto> =
        followService.getFollowsByFollowerId(followerId, pageable)
            .map { follow ->
                GetFolloweeResponseDto.from(follow)
            }

}