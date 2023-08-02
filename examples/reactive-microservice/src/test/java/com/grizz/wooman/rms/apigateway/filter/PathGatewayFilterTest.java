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

@ActiveProfiles("gatewayfilter-path")
@AutoConfigureWebTestClient
@SpringBootTest(
        classes = ApiGatewayApplication.class
)
public class PathGatewayFilterTest {
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
    void prefixPath() {
        // when
        webTestClient.get()
                .uri("/prefix")
                .exchange()
                .expectStatus().isOk();

        // then
        var request = mockWebServer.takeRequest();
        var value = request.getPath();
        assertEquals("/hello/prefix", value);
    }

    @SneakyThrows
    @Test
    void stripPath() {
        // when
        webTestClient.get()
                .uri("/strip/a/b/hello/abc")
                .exchange()
                .expectStatus().isOk();

        // then
        var request = mockWebServer.takeRequest();
        var value = request.getPath();
        assertEquals("/hello/abc", value);
    }

    @SneakyThrows
    @Test
    void setPath() {
        // when
        webTestClient.get()
                .uri("/set/abc")
                .exchange()
                .expectStatus().isOk();

        // then
        var request = mockWebServer.takeRequest();
        var value = request.getPath();
        assertEquals("/hello/abc", value);
    }

    @SneakyThrows
    @Test
    void rewritePath() {
        // when
        webTestClient.get()
                .uri("/rewrite/abc/def")
                .exchange()
                .expectStatus().isOk();

        // then
        var request = mockWebServer.takeRequest();
        var value = request.getPath();
        assertEquals("/hello/abc/def/hoi", value);
    }
}
