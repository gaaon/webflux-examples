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

@ActiveProfiles("gatewayfilter-request-param")
@AutoConfigureWebTestClient
@SpringBootTest(
        classes = ApiGatewayApplication.class
)
public class RequestParamGatewayFilterTest {
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
    void addRequestParam() {
        // when
        webTestClient.get()
                .uri("/add")
                .exchange()
                .expectStatus().isOk();

        // then
        var request = mockWebServer.takeRequest();
        var path = request.getPath();
        assertEquals("/add?greeting=hello", path);
    }

    @SneakyThrows
    @Test
    void addRequestParamIfExists() {
        // when
        webTestClient.get()
                .uri("/add?greeting=hi")
                .exchange()
                .expectStatus().isOk();

        // then
        var request = mockWebServer.takeRequest();
        var path = request.getPath();
        assertEquals("/add?greeting=hi&greeting=hello", path);
    }

    @SneakyThrows
    @Test
    void removeRequestParam() {
        // when
        webTestClient.get()
                .uri("/remove?greeting=hello")
                .exchange()
                .expectStatus().isOk();

        // then
        var request = mockWebServer.takeRequest();
        var path = request.getPath();
        assertEquals("/remove", path);
    }
}
