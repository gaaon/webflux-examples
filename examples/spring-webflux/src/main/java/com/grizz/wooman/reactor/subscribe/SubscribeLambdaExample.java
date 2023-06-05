package com.grizz.wooman.reactor.subscribe;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

import java.util.List;

@Slf4j
public class SubscribeLambdaExample {
    public static void main(String[] args) {
        log.info("start main");
        Flux.fromIterable(List.of(1, 2, 3, 4, 5))
                .subscribe(value -> {
                    log.info("value: " + value);
                }, error -> {
                    log.error("error: " + error);
                }, () -> {
                    log.info("complete");
                }, Context.empty());
        log.info("end main");
    }
}
