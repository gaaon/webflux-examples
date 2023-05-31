package com.grizz.wooman.reactivestreamsimpl.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleCompletableObserver implements CompletableObserver {
    private Disposable disposable;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        log.info("subscribe");
        this.disposable = d;
    }

    @Override
    public void onComplete() {
        log.info("complete");
    }

    @Override
    public void onError(@NonNull Throwable e) {
        log.error("error: {}", e.getMessage());
    }
}
