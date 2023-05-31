package com.grizz.wooman.reactivestreamsimpl.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleMaybeObserver<T> implements MaybeObserver<T> {
    private Disposable disposable;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.disposable = d;
        log.info("subscribe");
    }

    @Override
    public void onSuccess(@NonNull T t) {
        log.info("item: {}", t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        log.error("error: {}", e.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("complete");
    }
}
