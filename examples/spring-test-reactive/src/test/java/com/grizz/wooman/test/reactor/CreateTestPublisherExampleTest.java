package com.grizz.wooman.test.reactor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.test.publisher.TestPublisher;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@Slf4j
public class CreateTestPublisherExampleTest {
    @Test
    void test1() {
        TestPublisher<Integer> testPublisher = TestPublisher.create();

        assertInstanceOf(Publisher.class, testPublisher);
    }
}
