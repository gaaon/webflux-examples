package com.grizz.wooman.reactor.error;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ErrorNoHandleExample {
    public static void main(String[] args) {
        log.info("start main");
        Flux.create(sink -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sink.error(new RuntimeException("error"));
        }).subscribe();
        log.info("end main");
    }
}
