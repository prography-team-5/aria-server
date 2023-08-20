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
    fun getFollowers(followeeId: Long, pageable: Pageable): ResponseEntity<Slice<GetFollowerResponseDto>> =
        ResponseEntity(followUseCase.getFollowers(followeeId, pageable), OK)

    @Operation(summary = "Get my follower list API")
    @GetMapping("/followers/me")
    fun getMyFollowers(pageable: Pageable): ResponseEntity<Slice<GetFollowerResponseDto>> =
        ResponseEntity(followUseCase.getMyFollowers(pageable), OK)


    @Operation(summary = "Get followee list API")
    @GetMapping("/followees")
    fun getFollowees(followerId: Long, pageable: Pageable): ResponseEntity<Slice<GetFolloweeResponseDto>> =
        ResponseEntity(followUseCase.getFollowees(followerId, pageable), OK)


    @Operation(summary = "Get my followee list API")
    @GetMapping("/followees/me")
    fun getMyFollowees(pageable: Pageable): ResponseEntity<Slice<GetFolloweeResponseDto>> =
        ResponseEntity(followUseCase.getMyFollowees(pageable), OK)


}