package com.aria.server.art.infrastructure

import com.aria.server.art.infrastructure.dto.CreateArtRequest
import com.aria.server.art.infrastructure.dto.CreateArtResponse
import com.aria.server.art.infrastructure.dto.GetRandomArtResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Art", description = "Art API Document")
@RestController
@RequestMapping("/v1/arts")
class ArtController(
    private val artService: ArtService
) {

    @Operation(summary = "Create art API")
    @PostMapping
    @ResponseStatus(OK)
    fun createArt(@RequestBody request: CreateArtRequest): ResponseEntity<CreateArtResponse> {
        val response = artService.createArt(request)
        return ResponseEntity(response, CREATED)
    }

    @Operation(summary = "Get random art API")
    @GetMapping("/random")
    @ResponseStatus(OK)
    fun getRandomArt(): ResponseEntity<GetRandomArtResponse> {
        val response = artService.getRandomArt()
        return ResponseEntity(response, OK)
    }
}
