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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("example")
@AutoConfigureWebTestClient
@SpringBootTest(
        classes = ApiGatewayApplication.class
)
public class ExampleTest {
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

    @Test
    void test1() {
        // given
        mockWebServer.enqueue(new MockResponse());

        // when, then
        webTestClient.get()
                .uri("/hello")
                .header("X-I-AM", "token")
                .exchange()
                .expectStatus().isCreated();
    }

    @SneakyThrows
    @Test
    void test2() {
        // given
        mockWebServer.enqueue(new MockResponse());

        // when, then
        webTestClient.get()
                .uri("/hola/abc/def")
                .exchange()
                .expectStatus().isOk();

        var request = mockWebServer.takeRequest();
        assertEquals("/abc/def", request.getPath());
    }
}
