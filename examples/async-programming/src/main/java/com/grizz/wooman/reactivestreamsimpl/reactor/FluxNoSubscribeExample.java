package com.grizz.wooman.reactivestreamsimpl.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxNoSubscribeExample {
    public static void main(String[] args) {
        log.info("start main");
        getItems();
        log.info("end main");
    }

    private static Flux<Integer> getItems() {
        return Flux.create(fluxSink -> {
            log.info("start getItems");
            for (int i = 0; i < 5; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
            log.info("end getItems");
        });
    }
}
