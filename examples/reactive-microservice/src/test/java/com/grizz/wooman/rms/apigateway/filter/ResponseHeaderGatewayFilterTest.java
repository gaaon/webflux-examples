package com.grizz.wooman.rms.apigateway.filter;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ActiveProfiles("gatewayfilter-response-header")
@AutoConfigureWebTestClient
@SpringBootTest(
        classes = ApiGatewayApplication.class
)
public class ResponseHeaderGatewayFilterTest {
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
    void addResponseHeader() {
        // given
        mockWebServer.enqueue(new MockResponse());

        // when, then
        webTestClient.get()
                .uri("/add")
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .valueEquals("X-Test", "hola");
    }

    @SneakyThrows
    @Test
    void addResponseHeaderIfExists() {
        // given
        mockWebServer.enqueue(new MockResponse()
                .setHeader("X-Test", "hi"));

        // when, then
        webTestClient.get()
                .uri("/add")
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .valueEquals("X-Test", "hola", "hi");
    }

    @SneakyThrows
    @Test
    void setResponseHeader() {
        // given
        mockWebServer.enqueue(new MockResponse()
                .setHeader("X-Test", "hi"));

        // when, then
        webTestClient.get()
                .uri("/set")
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .valueEquals("X-Test", "hola");
    }

    @SneakyThrows
    @Test
    void rewriteResponseHeader() {
        // given
        mockWebServer.enqueue(new MockResponse()
                .setHeader("X-Test", "abchi"));

        // when, then
        webTestClient.get()
                .uri("/rewrite")
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .valueEquals("X-Test", "abchola");
    }

    @SneakyThrows
    @Test
    void removeResponseHeader() {
        // given
        mockWebServer.enqueue(new MockResponse()
                .setHeader("X-Test", "hi"));

        // when, then
        webTestClient.get()
                .uri("/remove")
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .doesNotExist("X-Test");
    }
}
