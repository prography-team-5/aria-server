package com.aria.server.art.infrastructure

import com.aria.server.art.infrastructure.dto.CreateArtImageResponse
import com.aria.server.art.infrastructure.dto.CreateArtRequest
import com.aria.server.art.infrastructure.dto.CreateArtResponse
import com.aria.server.art.infrastructure.dto.GetRandomArtResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/arts")
class ArtController(
    private val artService: ArtService,
) {

    @PostMapping
    fun createArt(@RequestBody request: CreateArtRequest): ResponseEntity<CreateArtResponse> {
        val response = artService.createArt(request)
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @PostMapping("/image")
    fun createArtImage(@RequestParam("image") artImage: MultipartFile): ResponseEntity<CreateArtImageResponse> {
        val response = artService.createArtImage(artImage)
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @GetMapping("/random")
    fun getRandomArt(): ResponseEntity<GetRandomArtResponse> {
        val response = artService.getRandomArt()
        return ResponseEntity(response, HttpStatus.OK)
    }
}
