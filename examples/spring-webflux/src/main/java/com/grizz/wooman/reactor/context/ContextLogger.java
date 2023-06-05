package com.grizz.wooman.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class ContextLogger {
    public static <T> Mono<T> logContext(T t, String name) {
        return Mono.deferContextual(c -> {
            log.info("name: {}, context: {}", name, c);
            return Mono.just(t);
        });
    }
}
