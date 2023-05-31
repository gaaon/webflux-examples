package com.grizz.wooman.reactivestreamsimpl.mutiny;

import io.smallrye.mutiny.subscription.MultiSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;

@Slf4j
@RequiredArgsConstructor
public class SimpleMultiSubscriber<T> implements MultiSubscriber<T> {
    private final Integer count;

    @Override
    public void onSubscribe(Flow.Subscription s) {
        s.request(count);
        log.info("subscribe");
    }

    @Override
    public void onItem(T item) {
        log.info("item: {}", item);
    }

    @Override
    public void onFailure(Throwable failure) {
        log.error("fail: {}", failure.getMessage());
    }

    @Override
    public void onCompletion() {
        log.info("completion");
    }
}
