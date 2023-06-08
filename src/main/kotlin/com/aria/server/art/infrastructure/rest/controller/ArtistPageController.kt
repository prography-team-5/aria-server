package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.CreateArtistInfoRequestDto
import com.aria.server.art.infrastructure.rest.dto.CreateSocialLinkRequestDto
import com.aria.server.art.infrastructure.rest.dto.CreateTagRequestDto
import com.aria.server.art.infrastructure.rest.response.Response
import com.aria.server.art.infrastructure.rest.response.Response.Companion.success
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*

@Tag(name = "Artist Page", description = "Artist Page API Document")
@RestController
@RequestMapping("/v1/artist-page")
class ArtistPageController (
    private val artistPageUseCase: ArtistPageUseCase
){
    @Operation(summary = "Get Artist Page API")
    @GetMapping("")
    @ResponseStatus(OK)
    fun getArtistPage(artistId: Long): Response =
        success(OK.reasonPhrase, artistPageUseCase.getArtistPage(artistId))

    @Operation(summary = "Create Artist Info API")
    @PostMapping("/artist-info")
    @ResponseStatus(CREATED)
    fun createArtistInfo(dto: CreateArtistInfoRequestDto): Response {
        artistPageUseCase.createArtistInfo(dto)
        return success(CREATED.reasonPhrase)
    }

    @Operation(summary = "Create Tag API")
    @PostMapping("/tag")
    @ResponseStatus(CREATED)
    fun createTag(dto: CreateTagRequestDto): Response {
        artistPageUseCase.createTag(dto)
        return success(CREATED.reasonPhrase)
    }

    @Operation(summary = "Delete Tag API")
    @DeleteMapping("/tag")
    @ResponseStatus(OK)
    fun deleteTag(id: Long): Response {
        artistPageUseCase.deleteTag(id)
        return success(OK.reasonPhrase)
    }

    @Operation(summary = "Create Social Link API")
    @PostMapping("/social-link")
    @ResponseStatus(CREATED)
    fun createSocialLink(dto: CreateSocialLinkRequestDto): Response {
        artistPageUseCase.createSocialLink(dto)
        return success(CREATED.reasonPhrase)
    }

    @Operation(summary = "Delete Social Link API")
    @DeleteMapping("/social-link")
    @ResponseStatus(OK)
    fun deleteSocialLink(id: Long): Response {
        artistPageUseCase.deleteSocialLink(id)
        return success(OK.reasonPhrase)
    }


}