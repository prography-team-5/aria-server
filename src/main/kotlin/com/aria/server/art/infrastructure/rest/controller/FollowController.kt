package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.response.Response
import com.aria.server.art.infrastructure.rest.response.Response.Companion.success
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
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
    @ResponseStatus(OK)
    fun unfollow(followeeId: Long): Response {
        followUseCase.unFollow(followeeId)
        return success(OK.reasonPhrase)
    }

    @Operation(summary = "Get follower list API")
    @GetMapping("/followers")
    @ResponseStatus(OK)
    fun getFollowers(followeeId: Long, pageable: Pageable): Response {
        return success(OK.reasonPhrase, followUseCase.getFollowers(followeeId, pageable))
    }

    @Operation(summary = "Get my follower list API")
    @GetMapping("/followers/me")
    @ResponseStatus(OK)
    fun getMyFollowers(pageable: Pageable): Response {
        return success(OK.reasonPhrase, followUseCase.getMyFollowers(pageable))
    }

    @Operation(summary = "Get followee list API")
    @GetMapping("/followees")
    @ResponseStatus(OK)
    fun getFollowees(followerId: Long, pageable: Pageable): Response {
        return success(OK.reasonPhrase, followUseCase.getFollowees(followerId, pageable))
    }

    @Operation(summary = "Get my followee list API")
    @GetMapping("/followees/me")
    @ResponseStatus(OK)
    fun getMyFollowees(pageable: Pageable): Response {
        return success(OK.reasonPhrase, followUseCase.getMyFollowees(pageable))
    }

}