package com.grizz.wooman.reactivestreamsimpl.mutiny;

import io.smallrye.mutiny.Multi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiExample {
    public static void main(String[] args) {
        getItems()
                .subscribe()
                .withSubscriber(
                        new SimpleMultiSubscriber<>(Integer.MAX_VALUE)
                );
    }

    private static Multi<Integer> getItems() {
        return Multi.createFrom().items(1, 2, 3, 4, 5);
    }
}
