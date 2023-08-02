package com.grizz.wooman.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class TooLongPublisherExampleTest {
    @Test
    void test1() {
        var mono = Mono.delay(Duration.ofHours(1))
                        .map(it -> 1);

        StepVerifier.create(mono)
                .expectNext(1)
                .verifyComplete();
    }

    @Test
    void test2() {
        var flux = Flux.range(0, 10)
                .delayElements(Duration.ofSeconds(10));

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();
    }
}
