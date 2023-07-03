package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.CreateArtistInfoRequestDto
import com.aria.server.art.infrastructure.rest.dto.CreateArtistTagRequestDto
import com.aria.server.art.infrastructure.rest.dto.CreateSocialLinkRequestDto
import com.aria.server.art.infrastructure.rest.response.Response
import com.aria.server.art.infrastructure.rest.response.Response.Companion.success
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*

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

    @Operation(summary = "Get Random Artist Info Detail API")
    @GetMapping("/rand")
    @ResponseStatus(OK)
    fun getRandArtistInfoDetail(): Response =
        success(OK.reasonPhrase, artistInfoDetailUseCase.getRandArtistInfoDetail())

    @Operation(summary = "Create Artist Info API")
    @PostMapping("/infos")
    @ResponseStatus(CREATED)
    fun createArtistInfo(dto: CreateArtistInfoRequestDto): Response {
        artistInfoDetailUseCase.createArtistInfo(dto)
        return success(CREATED.reasonPhrase)
    }

    @Operation(summary = "Create Artist Tag API")
    @PostMapping("/tags")
    @ResponseStatus(CREATED)
    fun createArtistTags(dto: CreateArtistTagRequestDto): Response {
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
    fun createSocialLink(dto: CreateSocialLinkRequestDto): Response {
        artistInfoDetailUseCase.createSocialLink(dto)
        return success(CREATED.reasonPhrase)
    }

    @Operation(summary = "Delete Social Link API")
    @DeleteMapping("/social-links")
    @ResponseStatus(OK)
    fun deleteSocialLink(id: Long): Response {
        artistInfoDetailUseCase.deleteSocialLink(id)
        return success(OK.reasonPhrase)
    }


}