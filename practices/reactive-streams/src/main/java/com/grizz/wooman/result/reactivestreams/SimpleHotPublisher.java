package com.grizz.wooman.result.reactivestreams;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

@Slf4j
public class SimpleHotPublisher implements Flow.Publisher<Integer> {
    private final ExecutorService publisherExecutor = Executors.newSingleThreadExecutor();
    private final List<Integer> numbers;
    private final List<SimpleHotSubscription> subscriptions = new ArrayList<>();
    private final Future<Void> task;

    public SimpleHotPublisher() {
        numbers = new ArrayList<>();
        numbers.add(1);

        task = publisherExecutor.submit(() -> {
            for (int i = 0; !Thread.interrupted(); i++) {
                numbers.add(i);
                subscriptions.forEach(SimpleHotSubscription::wakeup);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        int currentIndex = numbers.size() - 1;
        var s = new SimpleHotSubscription(currentIndex, subscriber);
        subscriber.onSubscribe(s);
        subscriptions.add(s);
    }

    public void shutdown() {
        task.cancel(true);
        publisherExecutor.shutdown();
    }

    private class SimpleHotSubscription implements Flow.Subscription {
        private int currentIndex;
        private int needsIndex;
        private final Flow.Subscriber<? super Integer> subscriber;
        private final ExecutorService subscriptionExecutor = Executors.newSingleThreadExecutor();

        public SimpleHotSubscription(int currentIndex, Flow.Subscriber<? super Integer> subscriber) {
            this.currentIndex = currentIndex;
            this.needsIndex = currentIndex;
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            needsIndex += n;
            onNextWhilePossible();
        }

        public void wakeup() {
            onNextWhilePossible();
        }

        private void onNextWhilePossible() {
            subscriptionExecutor.submit(() -> {
                while (currentIndex < numbers.size() && currentIndex < needsIndex) {
                    subscriber.onNext(numbers.get(currentIndex));
                    currentIndex++;
                }
            });
        }

        @Override
        public void cancel() {
            subscriber.onComplete();
            subscriptionExecutor.shutdown();
            subscriptions.remove(this);
        }
    }
}
