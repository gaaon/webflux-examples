package com.grizz.wooman.reactivestreamsimpl.mutiny;

import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UniExample {
    public static void main(String[] args) {
        getItem()
                .subscribe()
                .withSubscriber(new SimpleUniSubscriber<>(Integer.MAX_VALUE));
    }

    private static Uni<Integer> getItem() {
        return Uni.createFrom().item(1);
    }
}
