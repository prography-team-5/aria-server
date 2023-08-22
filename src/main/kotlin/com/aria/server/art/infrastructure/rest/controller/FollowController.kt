package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.FollowResponseDto
import com.aria.server.art.infrastructure.rest.dto.GetFolloweeResponseDto
import com.aria.server.art.infrastructure.rest.dto.GetFollowerResponseDto
import com.aria.server.art.infrastructure.rest.response.Response
import com.aria.server.art.infrastructure.rest.response.Response.Companion.success
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Follow", description = "Follow API Document")
@RestController
@RequestMapping("/v1/follows")
class FollowController (
    private val followUseCase: FollowUseCase
){

    @Operation(summary = "Follow API")
    @PostMapping("")
    fun follow(followeeId: Long): ResponseEntity<FollowResponseDto> =
        ResponseEntity(followUseCase.follow(followeeId), CREATED)

    @Operation(summary = "Unfollow API")
    @DeleteMapping("")
    fun unfollow(followeeId: Long): ResponseEntity<Unit> {
        followUseCase.unFollow(followeeId)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Get follower list API")
    @GetMapping("/followers")
    fun getFollowers(followeeId: Long): ResponseEntity<List<GetFollowerResponseDto>> =
        ResponseEntity(followUseCase.getFollowers(followeeId), OK)

    @Operation(summary = "Get my follower list API")
    @GetMapping("/followers/me")
    fun getMyFollowers(): ResponseEntity<List<GetFollowerResponseDto>> =
        ResponseEntity(followUseCase.getMyFollowers(), OK)


    @Operation(summary = "Get followee list API")
    @GetMapping("/followees")
    fun getFollowees(followerId: Long): ResponseEntity<List<GetFolloweeResponseDto>> =
        ResponseEntity(followUseCase.getFollowees(followerId), OK)


    @Operation(summary = "Get my followee list API")
    @GetMapping("/followees/me")
    fun getMyFollowees(): ResponseEntity<List<GetFolloweeResponseDto>> =
        ResponseEntity(followUseCase.getMyFollowees(), OK)


}