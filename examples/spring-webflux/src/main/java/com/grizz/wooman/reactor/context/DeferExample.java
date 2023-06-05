package com.grizz.wooman.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class DeferExample {
    public static void main(String[] args) {
        log.info("start main");
        Mono.defer(() -> {
            return Mono.just(1);
        }).subscribe(n -> {
            log.info("next: {}", n);
        });
        log.info("end main");
    }
}
