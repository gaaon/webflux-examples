package com.grizz.wooman.result.reactivestreams;

import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleColdPublisher implements Flow.Publisher<Integer> {
    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        var iterator = Collections.synchronizedList(
                IntStream.range(1, 10).boxed().collect(Collectors.toList())
        ).iterator();
        var subscription = new SimpleColdSubscription(iterator, subscriber);
        subscriber.onSubscribe(subscription);
    }

    @RequiredArgsConstructor
    public class SimpleColdSubscription implements Flow.Subscription {
        private final Iterator<Integer> iterator;
        private final Flow.Subscriber<? super Integer> subscriber;
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();

        @Override
        public void request(long n) {
            executorService.submit(() -> {
                for (int i = 0; i < n; i++) {
                    if (iterator.hasNext()) {
                        var number = iterator.next();
                        iterator.remove();
                        subscriber.onNext(number);
                    } else {
                        subscriber.onComplete();
                        executorService.shutdown();
                        break;
                    }
                }
            });
        }

        @Override
        public void cancel() {
            subscriber.onComplete();
        }
    }
}
