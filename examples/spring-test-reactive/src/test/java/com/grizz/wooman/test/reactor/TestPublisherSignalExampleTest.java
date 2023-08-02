package com.grizz.wooman.test.reactor;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

public class TestPublisherSignalExampleTest {
    @Test
    void test1() {
        var testPublisher = TestPublisher.create();

        StepVerifier.create(testPublisher)
                .then(() -> testPublisher.next(1, 2, 3))
                .expectNext(1, 2, 3)
                .then(() -> testPublisher.complete())
                .verifyComplete();
    }

    @Test
    void test2() {
        var testPublisher = TestPublisher.create();

        var error = new IllegalStateException("test");
        StepVerifier.create(testPublisher)
                .then(() -> testPublisher.error(error))
                .verifyErrorMatches(it -> it == error);
    }

    @Test
    void test3() {
        var testPublisher = TestPublisher.create();

        StepVerifier.create(testPublisher)
                .then(() -> testPublisher.emit(1, 2, 3))
                .expectNext(1, 2, 3)
                .verifyComplete();
    }
}
