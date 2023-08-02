package com.grizz.wooman.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class LastStepExpectErrorExampleTest {
    @Test
    void test1() {
        var flux = Flux.error(new IllegalStateException());
        StepVerifier.create(flux)
                .expectError()
                .verify();
    }

    @Test
    void test2() {
        var flux = Flux.error(new IllegalStateException());
        StepVerifier.create(flux)
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    void test3() {
        var message = "custom message";
        var flux = Flux.error(new IllegalStateException(message));
        StepVerifier.create(flux)
                .expectErrorMessage(message)
                .verify();
    }

    @Test
    void test4() {
        var flux = Flux.error(new IllegalStateException("hello"));
        StepVerifier.create(flux)
                .expectErrorMatches(e ->
                        e instanceof IllegalStateException &&
                                e.getMessage().equals("hello"))
                .verify();
    }

    @Test
    void test5() {
        var flux = Flux.error(new IllegalStateException("hello"));
        StepVerifier.create(flux)
                .expectErrorSatisfies(e -> {
                    assertInstanceOf(IllegalStateException.class, e);
                    assertEquals("hello", e.getMessage());
                })
                .verify();
    }
}
