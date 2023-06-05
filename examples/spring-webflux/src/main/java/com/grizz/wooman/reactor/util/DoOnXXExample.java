package com.grizz.wooman.reactor.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class DoOnXXExample {
    public static void main(String[] args) {
        log.info("start main");
        Flux.range(1, 5)
                .map(value -> value * 2)
                .doOnNext(value -> {
                    log.info("doOnNext: " + value);
                })
                .doOnComplete(() -> {
                    log.info("doOnComplete");
                })
                .doOnSubscribe(subscription -> {
                    log.info("doOnSubscribe");
                })
                .doOnRequest(value -> {
                    log.info("doOnRequest: " + value);
                })
                .map(value -> value / 2)
                .subscribe();
        log.info("end main");
    }
}
