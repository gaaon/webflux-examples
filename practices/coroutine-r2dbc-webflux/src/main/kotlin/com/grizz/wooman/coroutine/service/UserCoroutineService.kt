package com.grizz.wooman.coroutine.service

import com.grizz.wooman.coroutine.repository.UserR2dbcRepository
import com.grizz.wooman.webflux.common.EmptyImage
import com.grizz.wooman.webflux.common.Image
import com.grizz.wooman.webflux.common.User
import com.grizz.wooman.webflux.common.repository.AuthEntity
import com.grizz.wooman.webflux.common.repository.UserEntity
import com.grizz.wooman.webflux.service.ImageResponse
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import java.util.*
import java.util.Map

@Service
class UserCoroutineService(
    private val userRepository: UserR2dbcRepository,
    private val entityTemplate: R2dbcEntityTemplate
) {
    private val webClient = WebClient.create("http://localhost:8081")

    suspend fun findById(userId: String): User? {
        val userEntity = userRepository.findById(userId.toLong())
            ?: return null

        val imageId = userEntity.profileImageId
        val uriVariableMap = Map.of("imageId", imageId)

        val image = try {
            val imageResp = webClient.get()
                .uri("/api/images/{imageId}", uriVariableMap)
                .retrieve()
                .toEntity(ImageResponse::class.java)
                .map { resp: ResponseEntity<ImageResponse> -> resp.body }
                .awaitSingle()

            Image(
                imageResp.id,
                imageResp.name,
                imageResp.url
            )
        } catch (e: Exception) {
            EmptyImage()
        }

        var profileImage: Optional<Image> = Optional.empty()
        if (image !is EmptyImage) {
            profileImage = Optional.of(image)
        }
        return map(userEntity, profileImage)
    }

    @Transactional
    suspend fun createUser(
        name: String, age: Int,
        password: String, profileImageId: String
    ): User {
        val newUser = UserEntity(
            name,
            age,
            profileImageId,
            password
        )
        val userEntity = userRepository.save(newUser)

        val token = generateRandomToken()
        val auth = AuthEntity(userEntity.id, token)
        entityTemplate.insert(auth).awaitSingle()

        return map(
            userEntity,
            Optional.of(EmptyImage())
        )
    }

    private fun map(userEntity: UserEntity, profileImage: Optional<Image>): User {
        return User(
            userEntity.id.toString(),
            userEntity.name,
            userEntity.age,
            profileImage, listOf(),
            0L
        )
    }

    private fun generateRandomToken(): String {
        val token = StringBuilder()
        for (i in 0..5) {
            val item = ('A'.code.toDouble() + Math.random() * 26).toInt().toChar()
            token.append(item)
        }
        return token.toString()
    }
}