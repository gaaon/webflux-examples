package com.grizz.wooman.webflux;

import com.grizz.wooman.webflux.common.repository.AuthEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ActiveProfiles("integration")
@AutoConfigureWebTestClient
@SpringBootTest(
        properties = {
                "image.server.url=http://localhost:10000"
        }
)
public class UserIntegrationTest {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    R2dbcEntityTemplate r2dbcEntityTemplate;

    private MockWebServer mockWebServer;
    private final Integer mockWebServerPort = 10000;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
        mockWebServer.start(mockWebServerPort);
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        mockWebServer.shutdown();
    }

    @Test
    void contextLoad() {
    }

    @Nested
    class GetUserById {
        @Test
        void when_under_perfect_conditions_then_returns_user_resp() {
            // given
            var imageId = "123";
            var token = "validtoken";

            var userToSave = TestDataBuilder.createUnsavedUserEntity(
                    "grizz", 10, imageId, "123456"
            );
            var createdUser = r2dbcEntityTemplate.insert(
                    userToSave
            ).block();
            var userId = createdUser.getId();

            var authEntity = new AuthEntity(
                    userId, token
            );
            r2dbcEntityTemplate.insert(authEntity).block();

            mockWebServer.enqueue(new MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setBody("{\"id\": \"123\", \"name\": \"profile\", " +
                            "\"url\": \"http://grizz.kim/images/123\"}")
            );

            // when
            var result = webTestClient.get()
                    .uri("/api/users/" + userId)
                    .header("X-I-AM", token)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(userId)
                    .jsonPath("$.image.id").isEqualTo(imageId)
                    .returnResult();

            log.info("result: {}", result);

            // then
            assertEquals(1, mockWebServer.getRequestCount());
        }
    }
}
