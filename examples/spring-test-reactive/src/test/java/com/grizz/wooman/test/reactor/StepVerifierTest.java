package com.grizz.wooman.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class StepVerifierTest {
    @Test
    void test1() {
        Flux<Integer> flux = Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        });

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .expectComplete()
                .verify();

    }
}
