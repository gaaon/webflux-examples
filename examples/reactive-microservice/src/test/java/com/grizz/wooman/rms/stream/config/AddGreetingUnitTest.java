package com.grizz.wooman.rms.stream.config;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class AddGreetingUnitTest {
    StreamFunctionConfig streamFunctionConfig =
            new StreamFunctionConfig();

    @Test
    public void addGreeting() {
        // given
        var nameFlux = Flux.just("grizz", "taewoo", "wooman");

        // when
        var addGreetingFlux = streamFunctionConfig.addGreeting()
                .apply(nameFlux);

        // then
        StepVerifier.create(addGreetingFlux)
                .expectNext("Hello grizz!")
                .expectNext("Hello taewoo!")
                .expectNext("Hello wooman!")
                .verifyComplete();
    }
}
