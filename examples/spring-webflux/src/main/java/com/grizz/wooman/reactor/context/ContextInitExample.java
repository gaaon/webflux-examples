package com.grizz.wooman.reactor.context;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

@Slf4j
public class ContextInitExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        var initialContext = Context.of("name", "taewoo");

        Flux.just(1)
                .flatMap(v -> ContextLogger.logContext(v, "1"))
                .contextWrite(context ->
                        context.put("name", "wooman"))
                .flatMap(v -> ContextLogger.logContext(v, "2"))
                .subscribe(null, null, null, initialContext);
        log.info("end main");
    }
}
