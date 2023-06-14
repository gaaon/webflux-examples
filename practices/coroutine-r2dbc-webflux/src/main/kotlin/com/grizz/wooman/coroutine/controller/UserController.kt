package com.grizz.wooman.coroutine.controller

import com.grizz.wooman.coroutine.service.UserCoroutineService
import com.grizz.wooman.webflux.common.Image
import com.grizz.wooman.webflux.common.User
import com.grizz.wooman.webflux.controller.dto.ProfileImageResponse
import com.grizz.wooman.webflux.controller.dto.SignupUserRequest
import com.grizz.wooman.webflux.controller.dto.UserResponse
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@RequestMapping("/api/users")
@RestController
class UserController(
    private val userService: UserCoroutineService,
) {
    @GetMapping("/{userId}")
    suspend fun getUserById(
        @PathVariable userId: String
    ): UserResponse {
        val context = ReactiveSecurityContextHolder
            .getContext()
            .awaitSingle()

        val name = context.authentication.name
        if (name != userId) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }

        return userService.findById(userId)
            ?.let(this::map)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    suspend fun signupUser(
        @RequestBody request: SignupUserRequest
    ): UserResponse {
        return userService.createUser(
            request.name, request.age,
            request.password, request.profileImageId
        ).let(this::map)
    }

    private fun map(user: User): UserResponse {
        return UserResponse(
            user.id,
            user.name,
            user.age,
            user.followCount,
            user.profileImage.map { image: Image ->
                ProfileImageResponse(
                    image.id,
                    image.name,
                    image.url
                )
            }
        )
    }
}