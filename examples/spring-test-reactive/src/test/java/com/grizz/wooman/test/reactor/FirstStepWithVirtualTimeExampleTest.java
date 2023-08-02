package com.grizz.wooman.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FirstStepWithVirtualTimeExampleTest {
    @Test
    void test1() {
        StepVerifier.withVirtualTime(() -> {
                    return Flux.range(0, 5);
                })
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void test2() {
        var flux = Flux.range(0, 5)
                        .delayElements(Duration.ofSeconds(1));

        StepVerifier.withVirtualTime(() -> flux)
                .thenAwait(Duration.ofSeconds(5))
                .expectNextCount(5)
                .verifyComplete();
    }
}
