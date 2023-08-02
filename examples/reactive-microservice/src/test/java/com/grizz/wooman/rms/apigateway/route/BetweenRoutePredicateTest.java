package com.grizz.wooman.rms.apigateway.route;

import com.grizz.wooman.rms.apigateway.ApiGatewayApplication;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

@ActiveProfiles("between-predicate")
@AutoConfigureWebTestClient
@SpringBootTest(
        classes = ApiGatewayApplication.class
)
public class BetweenRoutePredicateTest {
    @Autowired
    private WebTestClient webTestClient;

    private MockWebServer mockWebServer;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8001);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @SneakyThrows
    @Test
    void test() {
        // given
        var message = "Hello world";
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(message)
        );

        // when, then
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .valueEquals("X-Test", "between-july")
                .expectBody(String.class)
                .isEqualTo(message);
    }
}
