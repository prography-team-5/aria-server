package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.GetFolloweeResponseDto
import com.aria.server.art.infrastructure.rest.dto.GetFollowerResponseDto
import com.aria.server.art.infrastructure.rest.response.Response
import com.aria.server.art.infrastructure.rest.response.Response.Companion.success
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.http.HttpStatus
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
    @ResponseStatus(CREATED)
    fun follow(followeeId: Long): Response {
        followUseCase.follow(followeeId)
        return success(CREATED.reasonPhrase)
    }

    @Operation(summary = "Unfollow API")
    @DeleteMapping("")
    fun unfollow(followId: Long): Response {
        followUseCase.unFollow(followId)
        return success(CREATED.reasonPhrase)
    }

    // TODO 로그인 시, 로그인 한 유저의 ID도 같이 줘야함
    @Operation(summary = "Get follower list API")
    @GetMapping("/followers")
    fun getFollowers(followeeId: Long, pageable: Pageable): Response {
        return success(OK.reasonPhrase, followUseCase.getFollowers(followeeId, pageable))
    }

    @Operation(summary = "Get followee list API")
    @GetMapping("/followees")
    fun getFollowees(followerId: Long, pageable: Pageable): Response {
        return success(OK.reasonPhrase, followUseCase.getFollowees(followerId, pageable))
    }

}