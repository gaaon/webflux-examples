package com.grizz.wooman.test.reactor;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.test.publisher.TestPublisher;

public class TestPublisherAssertTest {
    @Test
    void test1() {
        TestPublisher<Integer> testPublisher = TestPublisher.create();

        testPublisher.subscribe(new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(5);
            }

            @Override public void onNext(Object o) { }
            @Override public void onError(Throwable t) { }
            @Override public void onComplete() { }
        });
        testPublisher.assertSubscribers(1);
        testPublisher.assertWasRequested();
        testPublisher.assertMinRequested(5);
        testPublisher.assertMaxRequested(5);

        testPublisher.emit(1, 2, 3);
        testPublisher.assertNoSubscribers();
        testPublisher.assertWasNotCancelled();
    }
}
