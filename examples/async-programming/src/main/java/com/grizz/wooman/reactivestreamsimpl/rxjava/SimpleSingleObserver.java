package com.grizz.wooman.reactivestreamsimpl.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleSingleObserver<T> implements SingleObserver<T> {
    private Disposable disposable;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.disposable = d;
        log.info("subscribe");
    }

    @Override
    public void onSuccess(@NonNull Object o) {
        log.info("item: {}", o);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        log.error("error: {}", e.getMessage());
    }
}
