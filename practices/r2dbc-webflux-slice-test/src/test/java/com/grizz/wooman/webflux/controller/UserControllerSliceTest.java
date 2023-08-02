package com.grizz.wooman.webflux.controller;

import com.grizz.wooman.webflux.TestDataBuilder;
import com.grizz.wooman.webflux.service.AuthService;
import com.grizz.wooman.webflux.service.UserService;
import lombok.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = UserController.class)
class UserControllerSliceTest {
    @MockBean
    UserService userService;

    @MockBean
    AuthService authService;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void webTestClient_should_not_be_null() {
        assertNotNull(webTestClient);
    }

    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class UserRes {
        private String id;
        private String name;
        private int age;
        private Long followCount;
        private Optional<ProfileImageRes> image;
    }

    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class ProfileImageRes {
        private String id;
        private String name;
        private String url;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    private static class SignupUserReq {
        private String name;
        private Integer age;
        private String password;
        private String profileImageId;
    }

    @Nested
    class GetUserById {
        @Test
        void when_iam_token_is_not_given_then_returns_unauthorized_status() {
            // when, then
            webTestClient.get()
                    .uri("/api/users/1")
                    .exchange()
                    .expectStatus()
                    .isUnauthorized()
                    .expectStatus()
                    .is4xxClientError();
        }

        @Test
        void when_auth_service_returns_empty_then_returns_unauthorized_status() {
            // given
            var token = "invalidtoken";

            when(authService.getNameByToken(eq(token)))
                    .thenReturn(Mono.empty());

            // when, then
            webTestClient.get()
                    .uri("/api/users/1")
                    .header("x-i-am", token)
                    .exchange()
                    .expectStatus()
                    .isUnauthorized()
                    .expectStatus()
                    .is4xxClientError();
        }

        @Test
        void when_authentication_name_is_not_matched_then_returns_unauthorized_status() {
            // given
            var token = "token";
            var invalidName = "invalidName";

            when(authService.getNameByToken(eq(token)))
                    .thenReturn(Mono.just(invalidName));

            // when, then
            webTestClient.get()
                    .uri("/api/users/1")
                    .header("x-i-am", token)
                    .exchange()
                    .expectStatus()
                    .isUnauthorized()
                    .expectStatus()
                    .is4xxClientError();
        }

        @Test
        void when_user_service_returns_empty_then_returns_not_found_status() {
            // given
            var token = "token";
            var userId = "1234";

            when(authService.getNameByToken(eq(token)))
                    .thenReturn(Mono.just(userId));

            when(userService.findById(eq(userId)))
                    .thenReturn(Mono.empty());

            // when, then
            webTestClient.get()
                    .uri("/api/users/" + userId)
                    .header("x-i-am", token)
                    .exchange()
                    .expectStatus()
                    .isNotFound()
                    .expectStatus()
                    .is4xxClientError();
        }

        @Test
        void when_all_conditions_are_perfect_then_returns_user_resp() {
            // given
            var token = "token";
            var userId = "1234";
            var foundUser = TestDataBuilder.createUser(userId);

            when(authService.getNameByToken(eq(token)))
                    .thenReturn(Mono.just(userId));

            when(userService.findById(eq(userId)))
                    .thenReturn(Mono.just(foundUser));

            // when, then
            webTestClient.get()
                    .uri("/api/users/" + userId)
                    .header("x-i-am", token)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_JSON)
                    .expectBody(UserRes.class)
                    .value(userRes -> {
                        assertEquals(userId, userRes.id);
                        assertEquals(foundUser.getAge(), userRes.age);
                        assertEquals(foundUser.getFollowCount(), userRes.followCount);
                    });
        }
    }

    @Nested
    class SignupUser {
        @Test
        void when_create_user_returns_error_then_returns_status_400() {
            // given
            var name = "grizz";
            var age = 20;
            var profileImageId = "1";
            var password = "1q2w3e4r!";

            var signupRequest = new SignupUserReq(
                    name,
                    age,
                    password,
                    profileImageId
            );

            var error = new IllegalStateException("problem caused");
            when(userService.createUser(
                    anyString(),
                    anyInt(),
                    anyString(),
                    anyString()
            )).thenReturn(Mono.error(error));

            // when, then
            webTestClient.post()
                    .uri("/api/users/signup")
                    .bodyValue(signupRequest)
                    .exchange()
                    .expectStatus()
                    .is4xxClientError();
        }

        @Test
        void when_create_user_returns_empty_mono_then_returns_status_400() {
            // given
            var name = "grizz";
            var age = 20;
            var profileImageId = "1";
            var password = "1q2w3e4r!";

            var signupRequest = new SignupUserReq(
                    name,
                    age,
                    password,
                    profileImageId
            );

            when(userService.createUser(
                    anyString(),
                    anyInt(),
                    anyString(),
                    anyString()
            )).thenReturn(Mono.empty());

            // when, then
            webTestClient.post()
                    .uri("/api/users/signup")
                    .bodyValue(signupRequest)
                    .exchange()
                    .expectStatus()
                    .is4xxClientError();
        }

        @Test
        void when_signup_request_is_given_then_should_save_user() {
            // given
            var name = "grizz";
            var age = 20;
            var profileImageId = "1";
            var password = "1q2w3e4r!";
            var userId = "123456";

            var signupRequest = new SignupUserReq(
                    name,
                    age,
                    password,
                    profileImageId
            );

            var createdUser = TestDataBuilder.createUser(
                    userId,
                    name,
                    age,
                    password,
                    profileImageId
            );

            when(userService.createUser(
                    anyString(),
                    anyInt(),
                    anyString(),
                    anyString()
            )).thenReturn(Mono.just(createdUser));

            // when, then
            webTestClient.post()
                    .uri("/api/users/signup")
                    .bodyValue(signupRequest)
                    .exchange()
                    .expectStatus()
                    .isCreated()
                    .expectBody(UserRes.class)
                    .value(userRes -> {
                        assertEquals(userId, userRes.id);
                        assertEquals(createdUser.getAge(), userRes.age);
                        assertEquals(createdUser.getName(), userRes.name);
                    });
        }
    }
}