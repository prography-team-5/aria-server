package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.EditNicknameRequestDto
import com.aria.server.art.infrastructure.rest.response.Response
import com.aria.server.art.infrastructure.rest.response.Response.Companion.success
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.*

@Tag(name = "Member", description = "Member API Document")
@RestController
@RequestMapping("/v1/members")
class MemberController (
    private val memberUseCase: MemberUseCase
) {

    @Operation(summary = "Get Member Profile API")
    @GetMapping("")
    @ResponseStatus(OK)
    fun getMemberProfile(id: Long): Response =
        success(OK.reasonPhrase, memberUseCase.getMemberProfile(id))

    @Operation(summary = "Get My Profile API")
    @GetMapping("/me")
    @ResponseStatus(OK)
    fun getMyProfile(): Response =
        success(OK.reasonPhrase, memberUseCase.getMyProfile())

    @Operation(summary = "Edit Nickname API")
    @PatchMapping("/nickname")
    @ResponseStatus(OK)
    fun editNickname(@RequestBody dto: EditNicknameRequestDto): Response =
        success(OK.reasonPhrase, memberUseCase.editNickname(dto))

}