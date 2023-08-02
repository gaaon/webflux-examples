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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("gatewayfilter-request-header")
@AutoConfigureWebTestClient
@SpringBootTest(
        classes = ApiGatewayApplication.class
)
public class RequestHeaderGatewayFilterTest {
    @Autowired
    private WebTestClient webTestClient;

    private MockWebServer mockWebServer;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8001);
        mockWebServer.enqueue(new MockResponse());
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @SneakyThrows
    @Test
    void addRequestHeader() {
        // when
        webTestClient.get()
                .uri("/add")
                .exchange()
                .expectStatus().isOk();

        // then
        var request = mockWebServer.takeRequest();
        var value = request.getHeader("X-Test");
        assertEquals("hola", value);
    }

    @SneakyThrows
    @Test
    void addRequestHeaderIfExists() {
        // when
        webTestClient.get()
                .uri("/add")
                .header("X-Test", "hi")
                .exchange()
                .expectStatus().isOk();

        // then
        var request = mockWebServer.takeRequest();
        var values = request.getHeaders().values("X-Test");
        assertIterableEquals(List.of("hi", "hola"), values);
    }

    @SneakyThrows
    @Test
    void setRequestHeader() {
        // when
        webTestClient.get()
                .uri("/set")
                .header("X-Test", "hi")
                .exchange()
                .expectStatus().isOk();

        // then
        var request = mockWebServer.takeRequest();
        var value = request.getHeader("X-Test");
        assertEquals("hola", value);
    }

    @SneakyThrows
    @Test
    void mapRequestHeader() {
        // when
        webTestClient.get()
                .uri("/map")
                .header("X-Test", "hi")
                .exchange()
                .expectStatus().isOk();

        // then
        var request = mockWebServer.takeRequest();
        var value = request.getHeader("X-Test-Next");
        assertEquals("hi", value);

        var value2 = request.getHeader("X-Test");
        assertEquals("hi", value2);
    }

    @SneakyThrows
    @Test
    void removeRequestHeader() {
        // when
        webTestClient.get()
                .uri("/remove")
                .header("X-Test", "hi")
                .exchange()
                .expectStatus().isOk();

        // then
        var request = mockWebServer.takeRequest();
        var value = request.getHeader("X-Test");
        assertNull(value);
    }
}
