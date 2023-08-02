package com.grizz.wooman.rms.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

public class ReactorTransformExampleTest {
    @Test
    void reactorTransform() {
        // given
        var mono = Mono.just(1);

        // when
        var transformer = new Function<Mono<Integer>, Mono<Integer>>() {
            @Override
            public Mono<Integer> apply(Mono<Integer> mono) {
                return mono.map(it -> it + 1);
            }
        };

        // then
        StepVerifier.create(mono.transform(transformer))
                .expectNext(2)
                .verifyComplete();
    }

    @Test
    void reactorTransformWithError() {
        // given
        Flux<Integer> flux = Flux.error(new ArithmeticException());

        // when
        Function<Flux<Integer>, Flux<Integer>> transformer =
                (m) -> Flux.just(1);

        // then
        StepVerifier.create(flux.transform(transformer))
                .expectNext(1)
                .verifyComplete();
    }
}
