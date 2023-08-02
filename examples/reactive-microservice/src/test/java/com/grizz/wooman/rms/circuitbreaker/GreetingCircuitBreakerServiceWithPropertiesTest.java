package com.grizz.wooman.rms.circuitbreaker;

import com.grizz.wooman.rms.AutoConfigureReactiveCircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
@PropertySource(
        value = "classpath:application-circuitbreaker.yaml",
        factory = YamlPropertyLoaderFactory.class
)
@AutoConfigureReactiveCircuitBreaker
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = GreetingCircuitBreakerService.class
)
class GreetingCircuitBreakerServiceWithPropertiesTest {
    @Autowired
    private GreetingCircuitBreakerService greetingService;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @SpyBean
    private Greeter spyGreeter;

    private final String successMessage = "Hello grizz!";
    private final String fallbackMessage = "Hello world!";

    @BeforeEach
    void setUp() {
        var cbs = circuitBreakerRegistry.getAllCircuitBreakers();
        cbs.forEach(CircuitBreaker::reset);
    }

    @Test
    void contextLoad() {
    }

    @Test
    void greetingNoDelay() {
        // when, then
        var mono = greetingService.greeting("grizz", 0L);
        StepVerifier.create(mono)
                .expectNext(successMessage)
                .verifyComplete();

        verify(spyGreeter).generate("grizz");
    }

    @Test
    void greetingDelay5000AndWait1s() {
        // when, then
        StepVerifier.withVirtualTime(() ->
                        greetingService.greeting("grizz", 5000L))
                .thenAwait(Duration.ofSeconds(1))
                .expectNext(fallbackMessage)
                .verifyComplete();

        verify(spyGreeter, never()).generate("grizz");
    }

    @Test
    void greetingDelay5000AndWait5s() {
        // when, then
        StepVerifier.withVirtualTime(() ->
                        greetingService.greeting("grizz", 2000L))
                .thenAwait(Duration.ofSeconds(5))
                .expectNext(fallbackMessage)
                .verifyComplete();

        verify(spyGreeter, never()).generate("grizz");
    }

    @Test
    void greetingToThrowException() {
        // whe, then
        StepVerifier.create(
                greetingService.greetingWithException("grizz"))
                .expectNext(fallbackMessage)
                .verifyComplete();
    }

    @Test
    void makeCircuitBreakerOpen() {
        // when
        // 처음 4개는 성공
        for (int i = 0; i < 4; i++) {
            StepVerifier.create(
                            greetingService.greetingWithId(
                                    "mini", "grizz", 0L))
                    .expectNext(successMessage)
                    .verifyComplete();
        }

        // 5, 6번째를 실패로
        for (int i = 0; i < 2; i++) {
            StepVerifier.withVirtualTime(() ->
                            greetingService.greetingWithId(
                                    "mini", "grizz", 5000L))
                    .thenAwait(Duration.ofSeconds(2))
                    .expectNext(fallbackMessage)
                    .verifyComplete();
        }

        // circuit breaker 상태가 open
        // 이후에는 무조건 fallback
        for (int i = 0; i < 100; i++) {
            StepVerifier.create(
                            greetingService.greetingWithId(
                                    "mini", "grizz", 0L))
                    .expectNext(fallbackMessage)
                    .verifyComplete();
        }

        verify(spyGreeter, times(4)).generate("grizz");
    }


    @Test
    void makeCircuitBreakerHalfOpenManually() {
        // given
        for (int i = 0; i < 4; i++) {
            StepVerifier.withVirtualTime(() ->
                            greetingService.greetingWithId(
                                    "mini", "grizz", 5000L))
                    .thenAwait(Duration.ofSeconds(2))
                    .expectNext(fallbackMessage)
                    .verifyComplete();
        }

        // when
        var miniCb = circuitBreakerRegistry.circuitBreaker("mini");
        log.info("change state to half-open manually");
        miniCb.transitionToHalfOpenState();

        // then
        var state = circuitBreakerRegistry.circuitBreaker(
                "mini").getState();
        assertEquals(CircuitBreaker.State.HALF_OPEN, state);
        StepVerifier.create(
                        greetingService.greetingWithId(
                                "mini", "grizz", 0L))
                .expectNext(successMessage)
                .verifyComplete();
    }

    @SneakyThrows
    @Test
    void makeCircuitBreakerHalfOpenAutomatically() {
        // given
        for (int i = 0; i < 4; i++) {
            StepVerifier.withVirtualTime(() ->
                            greetingService.greetingWithId(
                                    "autoHalf", "grizz", 5000L))
                    .thenAwait(Duration.ofSeconds(2))
                    .expectNext(fallbackMessage)
                    .verifyComplete();
        }

        // when
        log.info("wait 6000ms");
        Thread.sleep(6000);


        // then
        var state = circuitBreakerRegistry.circuitBreaker(
                "autoHalf").getState();
        assertEquals(CircuitBreaker.State.HALF_OPEN, state);
        StepVerifier.create(
                        greetingService.greetingWithId(
                                "autoHalf", "grizz", 0L))
                .expectNext(successMessage)
                .verifyComplete();
    }

    @SneakyThrows
    @Test
    void makeCircuitBreakerHalfOpenToClose() {
        // given
        // half open 상태로 변경
        for (int i = 0; i < 4; i++) {
            StepVerifier.withVirtualTime(() ->
                            greetingService.greetingWithId(
                                    "halfOpen", "grizz", 5000L))
                    .thenAwait(Duration.ofSeconds(2))
                    .expectNext(fallbackMessage)
                    .verifyComplete();
        }

        log.info("wait 3000ms");
        Thread.sleep(3000);

        // when
        var total = 6;
        var failed = 2;

        // 4개는 성공
        for (int i = 0; i < total - failed; i++) {
            StepVerifier.create(
                            greetingService.greetingWithId(
                                    "halfOpen", "grizz", 0L))
                    .expectNext(successMessage)
                    .verifyComplete();
        }

        // 2개는 실패
        for (int i = 0; i < failed; i++) {
            StepVerifier.withVirtualTime(() ->
                            greetingService.greetingWithId(
                                    "halfOpen", "grizz", 5000L))
                    .thenAwait(Duration.ofSeconds(2))
                    .expectNext(fallbackMessage)
                    .verifyComplete();
        }

        // then
        var state = circuitBreakerRegistry.circuitBreaker("halfOpen")
                .getState();
        assertEquals(CircuitBreaker.State.CLOSED, state);
    }

    @SneakyThrows
    @Test
    void makeCircuitBreakerHalfOpenToOpen() {
        // given
        // half open 상태로 변경
        for (int i = 0; i < 4; i++) {
            StepVerifier.withVirtualTime(() ->
                            greetingService.greetingWithId(
                                    "halfOpen", "grizz", 5000L))
                    .thenAwait(Duration.ofSeconds(2))
                    .expectNext(fallbackMessage)
                    .verifyComplete();
        }

        log.info("wait 3000ms");
        Thread.sleep(3000);

        // when
        var total = 6;
        var failed = 3;

        // 4개는 성공
        for (int i = 0; i < total - failed; i++) {
            StepVerifier.create(
                            greetingService.greetingWithId(
                                    "halfOpen", "grizz", 0L))
                    .expectNext(successMessage)
                    .verifyComplete();
        }

        // 2개는 실패
        for (int i = 0; i < failed; i++) {
            StepVerifier.withVirtualTime(() ->
                            greetingService.greetingWithId(
                                    "halfOpen", "grizz", 5000L))
                    .thenAwait(Duration.ofSeconds(2))
                    .expectNext(fallbackMessage)
                    .verifyComplete();
        }

        // then
        var state = circuitBreakerRegistry.circuitBreaker("halfOpen")
                .getState();
        assertEquals(CircuitBreaker.State.OPEN, state);
    }
}