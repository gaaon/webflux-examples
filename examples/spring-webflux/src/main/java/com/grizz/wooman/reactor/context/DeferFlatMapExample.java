package com.grizz.wooman.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class DeferFlatMapExample {
    public static void main(String[] args) {
        log.info("start main");
        Mono.just(1)
                .flatMap(v -> Mono.defer(() -> {
                    return Mono.just(v);
                })).subscribe(n -> {
                    log.info("next: {}", n);
                });
        log.info("end main");
    }
}
