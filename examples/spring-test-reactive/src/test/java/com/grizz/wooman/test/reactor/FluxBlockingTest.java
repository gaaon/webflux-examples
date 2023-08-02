package com.grizz.wooman.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FluxBlockingTest {
    @Test
    void test1() {
        Flux<Integer> flux = Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        });

        var expected = IntStream.range(0, 10).boxed()
                .collect(Collectors.toList());
        var actual = flux.collectList().block();

        assertIterableEquals(expected, actual);
    }

    @Test
    void test2() {
        Flux<Integer> flux = Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
                if (i == 5) {
                    sink.error(new RuntimeException("error"));
                }
            }
            sink.complete();
        });

        assertThrows(RuntimeException.class, () -> {
            flux.collectList().block();
        });
    }

    @Test
    void test3() {
        Flux<Integer> flux = Flux.range(0, 10)
                .delayElements(Duration.ofSeconds(1));

        var expected = IntStream.range(0, 10).boxed()
                .collect(Collectors.toList());
        var actual = flux.collectList().block();

        assertIterableEquals(expected, actual);
    }
}
