package com.grizz.wooman.reactor.error;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class OnErrorResumeWithoutExecuteExample {
    public static void main(String[] args) {
        log.info("start main");
        Flux.just(1)
                .onErrorResume(e ->
                        Mono.just(shouldDoOnError()))
                .subscribe(value -> {
                    log.info("value: " + value);
                });
        log.info("end main");
    }

    private static int shouldDoOnError() {
        log.info("shouldDoOnError");
        return 0;
    }
}
