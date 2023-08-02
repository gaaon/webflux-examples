package com.grizz.wooman.rms.apigateway.filter;

import com.grizz.wooman.rms.apigateway.ApiGatewayApplication;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Import(TestScgCircuitBreakerConfig.class)
@ActiveProfiles({
        "gatewayfilter-circuitbreaker",
        "circuitbreaker",
})
@AutoConfigureWebTestClient
@SpringBootTest(
        classes = ApiGatewayApplication.class
)
public class CircuitBreakerGatewayFilterTest {
    @Autowired
    private WebTestClient webTestClient;

    private MockWebServer mockWebServer;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    private final String successMessage = "Hello world";
    private final String fallbackMessage = "fallback";

    @SneakyThrows
    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8001);
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        mockWebServer.shutdown();
        circuitBreakerRegistry.circuitBreaker("halfOpen")
                .reset();
    }

    @Test
    void test() {
        // given
        var slidingWindowSize = 4;
        mockWebServer.setDispatcher(
                new Dispatcher() {
                    @NotNull
                    @Override
                    public MockResponse dispatch(
                            @NotNull RecordedRequest recordedRequest)
                            throws InterruptedException {
                        Thread.sleep(2000);
                        return new MockResponse()
                                .setBody(successMessage);
                    }
                }
        );

        // when, then
        for (int i = 0; i < slidingWindowSize; i++) {
            webTestClient.get()
                    .uri("/hello")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(String.class)
                    .isEqualTo(fallbackMessage);
        }

        for (int i = 0; i < 100; i++) {
            webTestClient.get()
                    .uri("/hello")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(String.class)
                    .isEqualTo(fallbackMessage);
        }

        assertEquals(4, mockWebServer.getRequestCount());
    }

    @Test
    void test2() {
        // given
        var slidingWindowSize = 4;
        List<Integer> statusCodes = List.of(200, 200, 400, 500);

        for (Integer statusCode : statusCodes) {
            mockWebServer.enqueue(new MockResponse()
                    .setBody(successMessage)
                    .setResponseCode(statusCode));
        }

        // when, then
        for (int i = 0; i < slidingWindowSize; i++) {
            var expectedBody = successMessage;
            if (statusCodes.get(i) != 200) {
                expectedBody = fallbackMessage;
            }

            webTestClient.get()
                    .uri("/hello")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(String.class)
                    .isEqualTo(expectedBody);
        }

        assertEquals(4, mockWebServer.getRequestCount());
    }
}
