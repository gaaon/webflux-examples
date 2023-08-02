package com.grizz.wooman.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

public class StepContextExampleTest {
    @Test
    void test1() {
        var flux = Flux.range(0, 5);

        StepVerifier.create(flux)
                .expectNoAccessibleContext()
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void test2() {
        var flux = Flux.range(0, 5)
                .contextWrite(Context.of("foo", "bar"));

        StepVerifier.create(flux)
                .expectAccessibleContext()
                .contains("foo", "bar").then()
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void test3() {
        var flux = Flux.range(0, 5);

        var options = StepVerifierOptions.create()
                .withInitialContext(Context.of("foo", "bar"));

        StepVerifier.create(flux, options)
                .expectAccessibleContext()
                .contains("foo", "bar").then()
                .expectNextCount(5)
                .verifyComplete();
    }
}
