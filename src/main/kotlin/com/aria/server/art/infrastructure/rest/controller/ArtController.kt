package com.aria.server.art.infrastructure.rest.controller

import com.aria.server.art.infrastructure.rest.dto.CreateArtImageResponse
import com.aria.server.art.infrastructure.rest.dto.CreateArtRequest
import com.aria.server.art.infrastructure.rest.dto.CreateArtResponse
import com.aria.server.art.infrastructure.rest.dto.EditArtDescriptionRequest
import com.aria.server.art.infrastructure.rest.dto.EditArtImagesRequest
import com.aria.server.art.infrastructure.rest.dto.EditArtSizeRequest
import com.aria.server.art.infrastructure.rest.dto.EditArtStyleRequest
import com.aria.server.art.infrastructure.rest.dto.EditArtTagsRequest
import com.aria.server.art.infrastructure.rest.dto.EditArtTitleRequest
import com.aria.server.art.infrastructure.rest.dto.EditArtYearRequest
import com.aria.server.art.infrastructure.rest.dto.GetArtResponseDto
import com.aria.server.art.infrastructure.rest.dto.GetRandomArtResponseDto
import com.aria.server.art.infrastructure.rest.dto.SimpleArtDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
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
    @PostMapping(value = ["/image"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createArtImage(@RequestParam("image") artImage: MultipartFile): ResponseEntity<CreateArtImageResponse> {
        val response = artUseCase.createArtImage(artImage)
        return ResponseEntity(response, CREATED)
    }

    @Operation(summary = "Get random art API")
    @GetMapping("/random")
    fun getRandomArt(@RequestParam("count") count: Int): ResponseEntity<List<GetRandomArtResponseDto>> {
        val response = artUseCase.getRandomArts(count)
        return ResponseEntity(response, OK)
    }

    // TODO: sorting 조건 추가
    @Operation(summary = "Get arts API")
    @GetMapping("/artists/{artistId}")
    fun getArts(@PathVariable artistId: Long, @RequestParam("page") page: Int, @RequestParam("count") count: Int): ResponseEntity<List<SimpleArtDto>> {
        val response = artUseCase.getArts(artistId, page, count)
        return ResponseEntity(response, OK)
    }

    // TODO: sorting 조건 추가
    @Operation(summary = "Get my arts API")
    @GetMapping
    fun getMyArts(@RequestParam("page") page: Int, @RequestParam("count") count: Int): ResponseEntity<List<SimpleArtDto>> {
        val response = artUseCase.getMyArts(page, count)
        return ResponseEntity(response, OK)
    }

    @Operation(summary = "Get art API")
    @GetMapping("/{artId}")
    fun getArt(@PathVariable artId: Long): ResponseEntity<GetArtResponseDto> {
        val response = artUseCase.getArt(artId)
        return ResponseEntity(response, OK)
    }

    @Operation(summary = "Edit art images API")
    @PatchMapping("/images")
    fun editArtImages(@RequestBody dto: EditArtImagesRequest): ResponseEntity<Unit> {
        artUseCase.editArtImages(dto)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Edit art title API")
    @PatchMapping("/title")
    fun editArtTitle(@RequestBody dto: EditArtTitleRequest): ResponseEntity<Unit> {
        artUseCase.editArtTitle(dto)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Edit art year API")
    @PatchMapping("/year")
    fun editArtYear(@RequestBody dto: EditArtYearRequest): ResponseEntity<Unit> {
        artUseCase.editArtYear(dto)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Edit art size API")
    @PatchMapping("/size")
    fun editArtSize(@RequestBody dto: EditArtSizeRequest): ResponseEntity<Unit> {
        artUseCase.editArtSize(dto)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Edit art style API")
    @PatchMapping("/style")
    fun editArtStyle(@RequestBody dto: EditArtStyleRequest): ResponseEntity<Unit> {
        artUseCase.editArtStyle(dto)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Edit art tags API")
    @PatchMapping("/tags")
    fun editArtTags(@RequestBody dto: EditArtTagsRequest): ResponseEntity<Unit> {
        artUseCase.editArtTags(dto)
        return ResponseEntity(OK)
    }

    @Operation(summary = "Edit art description API")
    @PatchMapping("/description")
    fun editArtDescription(@RequestBody dto: EditArtDescriptionRequest): ResponseEntity<Unit> {
        artUseCase.editArtDescription(dto)
        return ResponseEntity(OK)
    }


    // TODO: 검색 고도화 (ex. 글자 단위 매칭)
    @Operation(summary = "Search arts API by art tag")
    @GetMapping("/search")
    fun searchArts(@RequestParam("query") query: String, @RequestParam("page") page: Int, @RequestParam("count")count: Int): ResponseEntity<List<SimpleArtDto>> {
        val response = artUseCase.searchArtsByArtTag(query, page, count)
        return ResponseEntity(response, OK)
    }
}
