package com.grizz.wooman.reactor.context;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ContextWriteExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        Flux.just(1)
                .flatMap(v -> ContextLogger.logContext(v, "1"))
                .contextWrite(context ->
                        context.put("name", "wooman"))
                .flatMap(v -> ContextLogger.logContext(v, "2"))
                .contextWrite(context ->
                        context.put("name", "taewoo"))
                .flatMap(v -> ContextLogger.logContext(v, "3"))
                .subscribe();
        log.info("end main");
    }
}
