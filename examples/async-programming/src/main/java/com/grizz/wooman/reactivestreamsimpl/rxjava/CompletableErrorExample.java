package com.grizz.wooman.reactivestreamsimpl.rxjava;

import io.reactivex.rxjava3.core.Completable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletableErrorExample {
    public static void main(String[] args) {
        getCompletion()
                .subscribe(new SimpleCompletableObserver());
    }

    private static Completable getCompletion() {
        return Completable.create(completableEmitter -> {
            Thread.sleep(1000);
            completableEmitter.onError(
                    new RuntimeException("error in completable")
            );
        });
    }
}
