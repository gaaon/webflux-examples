package com.grizz.study.controller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerAutoConfiguration;
import io.github.resilience4j.timelimiter.autoconfigure.TimeLimiterAutoConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JAutoConfiguration;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

@Slf4j
@Import(TestCircuitBreakerConfig.class)
@WebFluxTest(
        controllers = {GreetingController.class})
@ImportAutoConfiguration(
        classes = {
                ReactiveResilience4JAutoConfiguration.class,
                Resilience4JAutoConfiguration.class,
                CircuitBreakerAutoConfiguration.class,
                TimeLimiterAutoConfiguration.class
        }
)
class GreetingControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GreetingController greetingController;

    @Autowired
    private CircuitBreakerRegistry registry;

    @AfterEach
    void tearDown() {
        greetingController.reset();
    }

    @Test
    void contextLoads() {
    }

    private final String successMessage = "Hello world!\nToday's weather is sunny";
    private final String fallbackMessage = "Hello world!";

    private void expectSuccess(Long durationInMillis, TestInfo testInfo) {
        var displayName = testInfo.getTestMethod().get().getName();
        webTestClient.get()
                .uri("/greeting?waitTime={wt}&cbId={cb}",
                        Map.of("wt", durationInMillis,
                                "cb", displayName))
                .exchange()
                .expectBody(String.class)
                .isEqualTo(successMessage);
    }

    private void expectFailed(Long durationInMillis, TestInfo testInfo) {
        var displayName = testInfo.getTestMethod().get().getName();
        webTestClient.get()
                .uri("/greeting?waitTime={wt}&cbId={cb}",
                        Map.of("wt", durationInMillis,
                                "cb", displayName))
                .exchange()
                .expectBody(String.class)
                .isEqualTo(fallbackMessage);
    }

    @Test
    void normal(TestInfo testInfo) {
        // given
        var tc = 5;
        for (int i = 0; i < tc; i++) {
            expectSuccess(0L, testInfo);
        }
    }

    @SneakyThrows
    @Test
    void slow(TestInfo testInfo) {
        // given
        var tc = 5;
        for (int i = 0; i < tc; i++) {
            expectFailed(1500L, testInfo);
        }

        tc = 100;
        for (int i = 0; i < tc; i++) {
            expectFailed(0L, testInfo);
        }

        CircuitBreaker cb = registry.circuitBreaker(testInfo.getTestMethod().get().getName());
        cb.transitionToClosedState();

        tc = 100;
        for (int i = 0; i < tc; i++) {
            expectSuccess(0L, testInfo);
        }

    }

    @Nested
    class HalfOpenTest {
        @SneakyThrows
        @Test
        void halfOpenSuccess(TestInfo testInfo) {
            // given
            var tc = 5;

            // when
            // makes circuit breaker open
            for (int i = 0; i < tc; i++) {
                expectFailed(1500L, testInfo);
            }

            // wait for circuit breaker to half-open
            Thread.sleep(3000);
            log.info("waited 3 seconds");

            tc = 6;
            for (int i = 0; i < tc; i++) {
                expectSuccess(0L, testInfo);
            }

            expectSuccess(0L, testInfo);

            tc = 3;
            for (int i = 0; i < tc; i++) {
                expectFailed(1500L, testInfo);
            }

            expectFailed(0L, testInfo);
        }

        @SneakyThrows
        @Test
        void halfOpenFailed(TestInfo testInfo) {
            // given
            var tc = 5;

            // when
            // makes circuit breaker open
            for (int i = 0; i < tc; i++) {
                expectFailed(1500L, testInfo);
            }

            // wait for circuit breaker to half-open
            Thread.sleep(3000);
            log.info("waited 3 seconds");

            tc = 4;
            for (int i = 0; i < tc; i++) {
                expectSuccess(0L, testInfo);
            }

            for (int i = 0; i < 6 - tc; i++) {
                expectFailed(1500L, testInfo);
            }

            expectSuccess(0L, testInfo);
        }
    }
}