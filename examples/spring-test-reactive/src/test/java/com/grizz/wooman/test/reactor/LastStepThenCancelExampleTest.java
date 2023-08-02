package com.grizz.wooman.test.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
public class LastStepThenCancelExampleTest {
    @Test
    void test1() {
        var flux = Flux.range(0, 5)
                .doOnNext(i -> log.info("next: {}", i));

        StepVerifier.create(flux)
                .expectNextCount(3)
                .thenCancel()
                .verify();
    }
}
