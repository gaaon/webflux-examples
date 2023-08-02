package com.grizz.wooman.test.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;

@Slf4j
public class StepUtilityExampleTest {
    @Test
    void test1() {
        var flux = Flux.range(0, 10);

        StepVerifier.create(flux, 5)
                .expectSubscription()
                .expectNextCount(5)
                .as("five elements")
                .then(() -> log.info("five elements"))
                .thenRequest(5)
                .expectNextCount(5)
                .verifyComplete();
    }
}
