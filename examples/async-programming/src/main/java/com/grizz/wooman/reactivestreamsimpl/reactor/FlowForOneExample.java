package com.grizz.wooman.reactivestreamsimpl.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FlowForOneExample {
    public static void main(String[] args) {
        getOneItem()
                .subscribe(item -> log.info("item: {}", item));
    }

    private static Flux<Integer> getOneItem() {
        return Flux.create(sink -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sink.next(1);
            sink.complete();
        });
    }
}
