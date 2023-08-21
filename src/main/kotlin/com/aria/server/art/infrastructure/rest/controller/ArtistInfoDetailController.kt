package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.*
import com.aria.server.art.infrastructure.rest.response.Response
import com.aria.server.art.infrastructure.rest.response.Response.Companion.success
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Artist Info Detail", description = "Artist Info Detail API Document")
@RestController
@RequestMapping("/v1/artist-info-details")
class ArtistInfoDetailController (
    private val artistInfoDetailUseCase: ArtistInfoDetailUseCase
){
    @Operation(summary = "Get Artist Info Detail API")
    @GetMapping("")
    fun getArtistInfoDetail(artistId: Long): ResponseEntity<GetArtistInfoDetailResponseDto> =
         ResponseEntity(artistInfoDetailUseCase.getArtistInfoDetail(artistId), OK)

    @Operation(summary = "Get My Artist Info Detail API")
    @GetMapping("/me")
    fun getMyArtistInfoDetail(): ResponseEntity<GetArtistInfoDetailResponseDto> =
        ResponseEntity(artistInfoDetailUseCase.getMyArtistInfoDetail(), OK)

    @Operation(summary = "Get Random Artist Info Detail API")
    @GetMapping("/rand")
    @ResponseStatus(OK)
    fun getRandArtistInfoDetail(): ResponseEntity<GetArtistInfoDetailResponseDto> =
        ResponseEntity(artistInfoDetailUseCase.getRandArtistInfoDetail(), OK)

    @Operation(summary = "Create Artist Tag API")
    @PostMapping("/tags")
    fun createArtistTags(@RequestBody dto: CreateArtistTagRequestDto): ResponseEntity<CreateArtistTagResponseDto> =
        ResponseEntity(artistInfoDetailUseCase.createArtistTag(dto), CREATED)

    @Operation(summary = "Delete Artist Tag API")
    @DeleteMapping("/tags")
    fun deleteArtistTag(id: Long): ResponseEntity<Unit> {
        artistInfoDetailUseCase.deleteArtistTag(id)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Create Social Link API")
    @PostMapping("/social-links")
    fun createSocialLink(@RequestBody dto: CreateSocialLinkRequestDto): ResponseEntity<CreateSocialLinkResponseDto> =
        ResponseEntity(artistInfoDetailUseCase.createSocialLink(dto), CREATED)

    @Operation(summary = "Edit Social Link API")
    @PatchMapping("/social-links")
    fun editSocialLink(@RequestBody dto: EditSocialLinkRequestDto): ResponseEntity<Unit> {
        artistInfoDetailUseCase.editSocialLink(dto)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Delete Social Link API")
    @DeleteMapping("/social-links")
    fun deleteSocialLink(id: Long): ResponseEntity<Unit> {
        artistInfoDetailUseCase.deleteSocialLink(id)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Edit Artist Intro API")
    @PatchMapping("/intro")
    fun editArtistInfoIntro(@RequestBody dto: EditArtistInfoIntroRequestDto): ResponseEntity<Unit> {
        artistInfoDetailUseCase.editArtistInfoIntro(dto)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Change Profile Art Image API")
    @PostMapping(value = ["/profile-art-image"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun changeProfileArtImage(@RequestPart image: MultipartFile): ResponseEntity<Unit> {
        artistInfoDetailUseCase.changeProfileArtImage(image)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Delete Profile Art Image API")
    @DeleteMapping("/profile-art-image")
    @ResponseStatus(OK)
    fun deleteProfileArtImage(): ResponseEntity<Unit> {
        artistInfoDetailUseCase.deleteProfileArtImage()
        return ResponseEntity(OK)
    }
}