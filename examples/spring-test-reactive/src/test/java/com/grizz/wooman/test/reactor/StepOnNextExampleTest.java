package com.grizz.wooman.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepOnNextExampleTest {
    @Test
    void test1() {
        var flux = Flux.range(0, 10);

        StepVerifier.create(flux)
                .assertNext(i -> {
                    assertEquals(0, i);
                })
                .expectNext(1, 2)
                .expectNextCount(3)
                .expectNextSequence(List.of(6, 7, 8))
                .expectNextMatches(i -> i == 9)
                .expectComplete()
                .verify();
    }
}
