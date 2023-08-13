package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.EditNicknameRequestDto
import com.aria.server.art.infrastructure.rest.response.Response
import com.aria.server.art.infrastructure.rest.response.Response.Companion.success
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


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

    @Operation(summary = "Change Role To Artist API")
    @PostMapping("/role/artist")
    @ResponseStatus(CREATED)
    fun changeRoleToArtist(id: Long): Response {
        memberUseCase.changeRoleToArtist(id)
        return success(CREATED.reasonPhrase)
    }

    @Operation(summary = "Change Member Profile Image API")
    @PostMapping(value = ["/profile-image"], consumes = [MULTIPART_FORM_DATA_VALUE], produces = [APPLICATION_JSON_VALUE])
    @ResponseStatus(OK)
    fun changeProfileImage(@RequestPart image: MultipartFile): Response {
        memberUseCase.changeProfileImage(image)
        return success(CREATED.reasonPhrase)
    }

    @Operation(summary = "Delete Member Profile Image API")
    @DeleteMapping("/profile-image")
    @ResponseStatus(OK)
    fun deleteProfileImage(): Response {
        memberUseCase.deleteProfileImage()
        return success(OK.reasonPhrase)
    }

}