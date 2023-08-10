package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.CreateArtImageResponse
import com.aria.server.art.infrastructure.rest.dto.CreateArtRequest
import com.aria.server.art.infrastructure.rest.dto.CreateArtResponse
import com.aria.server.art.infrastructure.rest.dto.GetArtResponseDto
import com.aria.server.art.infrastructure.rest.dto.GetRandomArtResponseDto
import com.aria.server.art.infrastructure.rest.dto.SimpleArtDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
    private val artUseCase: ArtUseCase
) {

    @Operation(summary = "Create art API")
    @PostMapping
    fun createArt(@RequestBody request: CreateArtRequest): ResponseEntity<CreateArtResponse> {
        val response = artUseCase.createArt(request)
        return ResponseEntity(response, CREATED)
    }

    @Operation(summary = "Create art image API")
    @PostMapping("/image")
    fun createArtImage(@RequestParam("image") artImage: MultipartFile): ResponseEntity<CreateArtImageResponse> {
        val response = artUseCase.createArtImage(artImage)
        return ResponseEntity(response, CREATED)
    }

    @Operation(summary = "Get random art API")
    @GetMapping("/random")
    fun getRandomArt(@RequestParam("size") size: Int): ResponseEntity<List<GetRandomArtResponseDto>> {
        val response = artUseCase.getRandomArts(size)
        return ResponseEntity(response, OK)
    }

    // TODO: sorting 조건 추가
    @Operation(summary = "Get arts API")
    @GetMapping("/artists/{artistId}")
    fun getArts(@PathVariable artistId: Long, @RequestParam("page") page: Int, @RequestParam("size") size: Int): ResponseEntity<List<SimpleArtDto>> {
        val response = artUseCase.getArts(artistId, page, size)
        return ResponseEntity(response, OK)
    }

    // TODO: sorting 조건 추가
    @Operation(summary = "Get my arts API")
    @GetMapping
    fun getMyArts(@RequestParam("page") page: Int, @RequestParam("size") size: Int): ResponseEntity<List<SimpleArtDto>> {
        val response = artUseCase.getMyArts(page, size)
        return ResponseEntity(response, OK)
    }

    @Operation(summary = "Get art API")
    @GetMapping("/{artId}")
    fun getArt(@PathVariable artId: Long): ResponseEntity<GetArtResponseDto> {
        val response = artUseCase.getArt(artId)
        return ResponseEntity(response, OK)
    }
}
