package com.grizz.wooman.reactor.error;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class OnErrorReturnExample {
    public static void main(String[] args) {
        log.info("start main");
        Flux.error(new RuntimeException("error"))
                .onErrorReturn(0)
                .subscribe(value -> {
                    log.info("value: " + value);
                });
        log.info("end main");
    }
}
