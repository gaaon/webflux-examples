package com.grizz.wooman.reactivestreamsimpl.mutiny;

import io.smallrye.mutiny.subscription.UniSubscriber;
import io.smallrye.mutiny.subscription.UniSubscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SimpleUniSubscriber<T> implements UniSubscriber<T> {
    private final Integer count;
    private UniSubscription subscription;

    @Override
    public void onSubscribe(UniSubscription s) {
        this.subscription = s;
        s.request(1);
        log.info("subscribe");
    }

    @Override
    public void onItem(T item) {
        log.info("item: {}", item);
    }

    @Override
    public void onFailure(Throwable failure) {
        log.error("error: {}", failure.getMessage());
    }
}
