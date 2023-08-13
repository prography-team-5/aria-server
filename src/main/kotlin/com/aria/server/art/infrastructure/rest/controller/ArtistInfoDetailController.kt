package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.CreateArtistTagRequestDto
import com.aria.server.art.infrastructure.rest.dto.CreateSocialLinkRequestDto
import com.aria.server.art.infrastructure.rest.dto.EditArtistInfoIntroRequestDto
import com.aria.server.art.infrastructure.rest.dto.EditSocialLinkRequestDto
import com.aria.server.art.infrastructure.rest.response.Response
import com.aria.server.art.infrastructure.rest.response.Response.Companion.success
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType
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
    @ResponseStatus(OK)
    fun getArtistInfoDetail(artistId: Long): Response =
        success(OK.reasonPhrase, artistInfoDetailUseCase.getArtistInfoDetail(artistId))

    @Operation(summary = "Get My Artist Info Detail API")
    @GetMapping("/me")
    @ResponseStatus(OK)
    fun getMyArtistInfoDetail(): Response =
        success(OK.reasonPhrase, artistInfoDetailUseCase.getMyArtistInfoDetail())

    @Operation(summary = "Get Random Artist Info Detail API")
    @GetMapping("/rand")
    @ResponseStatus(OK)
    fun getRandArtistInfoDetail(): Response =
        success(OK.reasonPhrase, artistInfoDetailUseCase.getRandArtistInfoDetail())

    @Operation(summary = "Create Artist Tag API")
    @PostMapping("/tags")
    @ResponseStatus(CREATED)
    fun createArtistTags(@RequestBody dto: CreateArtistTagRequestDto): Response {
        artistInfoDetailUseCase.createArtistTag(dto)
        return success(CREATED.reasonPhrase)
    }

    @Operation(summary = "Delete Artist Tag API")
    @DeleteMapping("/tags")
    @ResponseStatus(OK)
    fun deleteArtistTag(id: Long): Response {
        artistInfoDetailUseCase.deleteArtistTag(id)
        return success(OK.reasonPhrase)
    }

    @Operation(summary = "Create Social Link API")
    @PostMapping("/social-links")
    @ResponseStatus(CREATED)
    fun createSocialLink(@RequestBody dto: CreateSocialLinkRequestDto): Response {
        artistInfoDetailUseCase.createSocialLink(dto)
        return success(CREATED.reasonPhrase)
    }

    @Operation(summary = "Edit Social Link API")
    @PatchMapping("/social-links")
    @ResponseStatus(OK)
    fun editSocialLink(@RequestParam id: Long, @RequestBody dto: EditSocialLinkRequestDto): Response {
        artistInfoDetailUseCase.editSocialLink(id, dto)
        return success(OK.reasonPhrase)
    }

    @Operation(summary = "Delete Social Link API")
    @DeleteMapping("/social-links")
    @ResponseStatus(OK)
    fun deleteSocialLink(id: Long): Response {
        artistInfoDetailUseCase.deleteSocialLink(id)
        return success(OK.reasonPhrase)
    }

    @Operation(summary = "Edit Artist Intro API")
    @PatchMapping("/intro")
    @ResponseStatus(OK)
    fun editArtistInfoIntro(@RequestBody dto: EditArtistInfoIntroRequestDto): Response {
        artistInfoDetailUseCase.editArtistInfoIntro(dto)
        return success(OK.reasonPhrase)
    }

    @Operation(summary = "Change Profile Art Image API")
    @PostMapping(value = ["/profile-art-image"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(CREATED)
    fun changeProfileArtImage(@RequestPart image: MultipartFile): Response {
        artistInfoDetailUseCase.changeProfileArtImage(image)
        return success(CREATED.reasonPhrase)
    }

    @Operation(summary = "Delete Profile Art Image API")
    @DeleteMapping("/profile-art-image")
    @ResponseStatus(OK)
    fun deleteProfileArtImage(): Response {
        artistInfoDetailUseCase.deleteProfileArtImage()
        return success(OK.reasonPhrase)
    }


}