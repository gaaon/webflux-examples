package com.grizz.wooman.test.reactor;

import com.grizz.wooman.test.TestToFail;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class LastStepExpectTimeoutExampleTest {
    @Test
    void test1() {
        var mono = Mono.delay(Duration.ofMillis(500));

        StepVerifier.create(mono)
                .expectTimeout(Duration.ofMillis(100))
                .verify();
    }

    @TestToFail
    void test2() {
        var mono = Mono.delay(Duration.ofMillis(100));

        StepVerifier.create(mono)
                .expectTimeout(Duration.ofSeconds(1))
                .verify();
    }

    @Test
    void test3() {
        var flux = Flux.range(0, 10)
                .delayElements(Duration.ofMillis(500));

        StepVerifier.create(flux)
                .expectTimeout(Duration.ofMillis(100))
                .verify();
    }
}
