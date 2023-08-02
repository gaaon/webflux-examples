package com.grizz.wooman.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class StepVerifierVerifyTest {
    @Test
    void test1() {
        StepVerifier
                .create(Flux.just(1, 2, 3))
                .expectNextCount(3)
                .expectComplete()
                .log()
                .verify();
    }

    @Test
    void test2() {
        var duration = Duration.ofSeconds(1);
        StepVerifier
                .create(Flux.just(1, 2, 3))
                .expectNextCount(3)
                .expectComplete()
                .log()
                .verifyThenAssertThat(duration)
                .tookLessThan(duration)
                .hasNotDroppedElements();

    }
}
