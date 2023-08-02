package com.grizz.wooman.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class LastStepVerifyExampleTest {
    @Test
    void test1() {
        var err = new IllegalStateException("hello");
        StepVerifier.create(Mono.error(err))
                .verifyErrorMessage("hello");
    }

    @Test
    void test2() {
        StepVerifier.create(Mono.just(1))
                .expectNext(1)
                .verifyComplete();
    }

    @Test
    void test3() {
        var mono = Mono.delay(Duration.ofMillis(500));
        StepVerifier.create(mono)
                .verifyTimeout(Duration.ofMillis(100));
    }
}
