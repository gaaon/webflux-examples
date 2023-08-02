package com.grizz.wooman.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class WithVirtualTimeTest {
    @Test
    void test1() {
        StepVerifier.withVirtualTime(() -> {
                    return Flux.range(0, 5)
                            .delayElements(Duration.ofHours(1));
                })
                .thenAwait(Duration.ofHours(2))
                .expectNextCount(2)
                .thenAwait(Duration.ofHours(3))
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void test2() {
        StepVerifier.withVirtualTime(() -> {
                    return Mono.delay(Duration.ofDays(10));
                })
                .expectSubscription()
                .expectNoEvent(Duration.ofDays(9))
                .thenAwait(Duration.ofDays(1))
                .expectNext(0L)
                .verifyComplete();
    }
}
