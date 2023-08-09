package com.campusgram.image.controller

import com.campusgram.image.service.ImageService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux

@RequestMapping("/api/images")
@RestController
class ImageController(
    private val imageService: ImageService,
) {
    @GetMapping("/{imageId}")
    suspend fun getImageById(
        @PathVariable imageId: Long,
    ): Image {
        return imageService.findImageById(imageId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @GetMapping
    fun getImagesByIds(
        @RequestParam imageIds: List<Long>,
    ): Flux<Image> {
        return imageService.findImagesByIds(imageIds)
    }
}