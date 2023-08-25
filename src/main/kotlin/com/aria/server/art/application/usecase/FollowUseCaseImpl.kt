package com.aria.server.art.application.usecase

import com.aria.server.art.infrastructure.rest.controller.FollowUseCase
import com.aria.server.art.infrastructure.rest.dto.FollowResponseDto
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
    override fun follow(followeeId: Long): FollowResponseDto {
        val follower = memberService.getCurrentMember()
        val followee = memberService.getMemberById(followeeId)
        val followId = followService.createFollow(follower, followee).id
        return FollowResponseDto(followId)
    }

    @Transactional
    override fun unFollow(followeeId: Long) {
        val follower = memberService.getCurrentMember()
        val followee = memberService.getMemberById(followeeId)
        followService.deleteFollow(follower, followee)
    }

    @Transactional(readOnly = true)
    override fun getFollowers(followeeId: Long): List<GetFollowerResponseDto> =
        followService.getFollowsByFolloweeId(followeeId)
            .map { follow -> GetFollowerResponseDto.from(follow) }

    @Transactional(readOnly = true)
    override fun getMyFollowers(): List<GetFollowerResponseDto> {
        val currentMemberId = memberService.getCurrentMember().id
        return followService.getFollowsByFolloweeId(currentMemberId)
            .map { follow -> GetFollowerResponseDto.from(follow) }
    }

    @Transactional(readOnly = true)
    override fun getFollowees(followerId: Long): List<GetFolloweeResponseDto> =
        followService.getFollowsByFollowerId(followerId)
            .map { follow -> GetFolloweeResponseDto.from(follow) }

    @Transactional(readOnly = true)
    override fun getMyFollowees(): List<GetFolloweeResponseDto> {
        val currentMemberId = memberService.getCurrentMember().id
        return followService.getFollowsByFollowerId(currentMemberId)
            .map { follow -> GetFolloweeResponseDto.from(follow) }
    }

// 프론트엔드 여건 상 페이지네이션 구현이 어렵기 때문에, 전체 팔로워 팔로이를 가져와 리스트로 전송하기로 결정, 추후 페이지네이션 구현
//    @Transactional(readOnly = true)
//    override fun getFollowers(followeeId: Long, pageable: Pageable): Slice<GetFollowerResponseDto> =
//        followService.getFollowsByFolloweeId(followeeId, pageable)
//            .map { follow -> GetFollowerResponseDto.from(follow) }
//
//    @Transactional(readOnly = true)
//    override fun getMyFollowers(pageable: Pageable): Slice<GetFollowerResponseDto> {
//        val currentMemberId = memberService.getCurrentMember().id
//        return followService.getFollowsByFolloweeId(currentMemberId, pageable)
//            .map { follow -> GetFollowerResponseDto.from(follow) }
//    }
//
//    @Transactional(readOnly = true)
//    override fun getFollowees(followerId: Long, pageable: Pageable): Slice<GetFolloweeResponseDto> =
//        followService.getFollowsByFollowerId(followerId, pageable)
//            .map { follow -> GetFolloweeResponseDto.from(follow) }
//
//    @Transactional(readOnly = true)
//    override fun getMyFollowees(pageable: Pageable): Slice<GetFolloweeResponseDto> {
//        val currentMemberId = memberService.getCurrentMember().id
//        return followService.getFollowsByFolloweeId(currentMemberId, pageable)
//            .map { follow -> GetFolloweeResponseDto.from(follow) }
//    }

}