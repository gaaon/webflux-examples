package com.grizz.wooman.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

public class FirstStepExampleTest {
    @Test
    void test1() {
        var flux = Flux.range(0, 5);

        var options = StepVerifierOptions.create()
                .initialRequest(100)
                .withInitialContext(Context.empty())
                .scenarioName("test1");

        StepVerifier.create(flux, options)
                .expectSubscription()
                .expectNextCount(5)
                .verifyComplete();
    }
}
