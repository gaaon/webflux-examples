package com.grizz.wooman.reactivestreamsimpl.rxjava;

import io.reactivex.rxjava3.core.Maybe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaybeExample {
    public static void main(String[] args) {
        maybeGetItem()
                .subscribe(new SimpleMaybeObserver<>());
    }

    private static Maybe<Integer> maybeGetItem() {
        return Maybe.create(maybeEmitter -> {
            maybeEmitter.onSuccess(1);
        });
    }
}
