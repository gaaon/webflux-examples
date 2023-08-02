package com.grizz.wooman.test.mockweb;

import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class MockWebServerEnqueueExampleTest {
    private MockWebServer mockWebServer;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        webTestClient = WebTestClient.bindToServer()
                .baseUrl(mockWebServer.url("").toString())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    private void getAndExpectBody(String expected) {
        webTestClient.get()
                .uri("/api/v1/users")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo(expected);
    }

    @Test
    void test() {
        // given
        mockWebServer.enqueue(
                new MockResponse().setBody("hello"));
        mockWebServer.enqueue(
                new MockResponse().setBody("hoi"));
        mockWebServer.enqueue(
                new MockResponse().setBody("hola"));
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(400)
                        .setHeader("X-WHO", "grizz")
        );

        // when
        getAndExpectBody("hello");
        getAndExpectBody("hoi");
        getAndExpectBody("hola");
        webTestClient.get()
                .uri("/api/v1/users")
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectHeader()
                .valueEquals("X-WHO", "grizz");

        assertEquals(4, mockWebServer.getRequestCount());
    }

    @Test
    void test2() {
        // given
        var dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(
                    @NotNull
                    RecordedRequest request) {

                if (request.getPath().equals("/api/v1/users")) {
                    return new MockResponse()
                            .setBody("hello");
                } else {
                    return new MockResponse()
                            .setResponseCode(404);
                }
            }
        };

        mockWebServer.setDispatcher(dispatcher);

        getAndExpectBody("hello");
        webTestClient.get()
                .uri("/no/such/path")
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
