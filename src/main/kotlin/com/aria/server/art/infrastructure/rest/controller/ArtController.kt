package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.CreateArtImageResponse
import com.aria.server.art.infrastructure.rest.dto.CreateArtRequest
import com.aria.server.art.infrastructure.rest.dto.CreateArtResponse
import com.aria.server.art.infrastructure.rest.dto.GetRandomArtResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Art", description = "Art API Document")
@RestController
@RequestMapping("/v1/arts")
class ArtController(
    private val artService: ArtService
) {

    @Operation(summary = "Create art API")
    @PostMapping
    fun createArt(@RequestBody request: CreateArtRequest): ResponseEntity<CreateArtResponse> {
        val response = artService.createArt(request)
        return ResponseEntity(response, CREATED)
    }

    @Operation(summary = "Create art image API")
    @PostMapping("/image")
    fun createArtImage(@RequestParam("image") artImage: MultipartFile): ResponseEntity<CreateArtImageResponse> {
        val response = artService.createArtImage(artImage)
        return ResponseEntity(response, CREATED)
    }

    @Operation(summary = "Get random art API")
    @GetMapping("/random")
    fun getRandomArt(): ResponseEntity<GetRandomArtResponse> {
        val response = artService.getRandomArt()
        return ResponseEntity(response, OK)
    }
}
