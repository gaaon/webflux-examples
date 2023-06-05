package com.grizz.wooman.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class CollectListExample {
    public static void main(String[] args) {
        log.info("start main");
        Flux.range(1, 5)
                .collectList()
                .doOnNext(value -> {
                    log.info("doOnNext: " + value);
                })
                .subscribe();
        log.info("end main");
    }
}
