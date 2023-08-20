package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.EditNicknameRequestDto
import com.aria.server.art.infrastructure.rest.dto.GetMemberProfileResponseDto
import com.aria.server.art.infrastructure.rest.response.Response
import com.aria.server.art.infrastructure.rest.response.Response.Companion.success
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
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
    fun getMemberProfile(id: Long): ResponseEntity<GetMemberProfileResponseDto> =
        ResponseEntity(memberUseCase.getMemberProfile(id), OK)

    @Operation(summary = "Get My Profile API")
    @GetMapping("/me")
    fun getMyProfile(): ResponseEntity<GetMemberProfileResponseDto> =
        ResponseEntity(memberUseCase.getMyProfile(), OK)

    @Operation(summary = "Edit Nickname API")
    @PatchMapping("/nickname")
    fun editNickname(@RequestBody dto: EditNicknameRequestDto): ResponseEntity<Unit> {
        memberUseCase.editNickname(dto)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Change Role To Artist API")
    @PostMapping("/role/artist")
    fun changeRoleToArtist(id: Long): ResponseEntity<Unit> {
        memberUseCase.changeRoleToArtist(id)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Change Member Profile Image API")
    @PostMapping(value = ["/profile-image"],
        consumes = [MULTIPART_FORM_DATA_VALUE],
        produces = [APPLICATION_JSON_VALUE])
    fun changeProfileImage(@RequestPart image: MultipartFile): ResponseEntity<Unit> {
        memberUseCase.changeProfileImage(image)
        return ResponseEntity(CREATED)
    }

    @Operation(summary = "Delete Member Profile Image API")
    @DeleteMapping("/profile-image")
    fun deleteProfileImage(): ResponseEntity<Unit> {
        memberUseCase.deleteProfileImage()
        return ResponseEntity(OK)
    }

    @Operation(summary = "Withdrawal API")
    @DeleteMapping("/withdrawal")
    @ResponseStatus(OK)
    fun withdrawal(): ResponseEntity<Unit> {
        memberUseCase.withdrawal()
        return ResponseEntity(OK)
    }

}