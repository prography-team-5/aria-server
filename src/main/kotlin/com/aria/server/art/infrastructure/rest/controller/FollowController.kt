package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.GetFolloweeResponseDto
import com.aria.server.art.infrastructure.rest.dto.GetFollowerResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Follow", description = "Follow API Document")
@RestController
@RequestMapping("/v1/follows")
class FollowController (
    private val followService: FollowService,
    private val memberService: MemberService
){

    @Operation(summary = "Follow API")
    @PostMapping("")
    fun follow(followeeId: Long): ResponseEntity<HttpStatus> {
        val follower = memberService.getCurrentMember()
        val followee = memberService.getMemberById(followeeId)
        followService.follow(follower, followee)
        return ResponseEntity<HttpStatus>(HttpStatus.CREATED)
    }

    @Operation(summary = "Unfollow API")
    @DeleteMapping("")
    fun unfollow(followId: Long): ResponseEntity<HttpStatus> {
        val follower = memberService.getCurrentMember()
        followService.unfollow(follower, followId)
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    // TODO 프론트와 얘기 후 Paging 도입 결정
    @Operation(summary = "Get my follower list API")
    @GetMapping("/followers/my")
    fun getMyFollowers(): ResponseEntity<List<GetFollowerResponseDto>>{
        val followee = memberService.getCurrentMember()
        return ResponseEntity(followService.getFollowers(followee), HttpStatus.OK)
    }

    @Operation(summary = "Get my followee list API")
    @GetMapping("/followees/my")
    fun getMyFollowees(): ResponseEntity<List<GetFolloweeResponseDto>>{
        val follower = memberService.getCurrentMember()
        return ResponseEntity(followService.getFollowees(follower), HttpStatus.OK)
    }

    @Operation(summary = "Get follower list API")
    @GetMapping("/followers")
    fun getFollowers(followeeId: Long): ResponseEntity<List<GetFollowerResponseDto>>{
        val followee = memberService.getMemberById(followeeId)
        return ResponseEntity(followService.getFollowers(followee), HttpStatus.OK)
    }

    @Operation(summary = "Get followee list API")
    @GetMapping("/followees")
    fun getFollowees(followerId: Long): ResponseEntity<List<GetFolloweeResponseDto>>{
        val follower = memberService.getMemberById(followerId)
        return ResponseEntity(followService.getFollowees(follower), HttpStatus.OK)
    }

}