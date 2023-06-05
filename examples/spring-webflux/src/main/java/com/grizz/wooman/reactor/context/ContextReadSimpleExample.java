package com.grizz.wooman.reactor.context;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

@Slf4j
public class ContextReadSimpleExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        var initialContext = Context.empty()
                .put("name", "taewoo");

        Flux.create(sink -> {
            var name = sink.contextView().get("name");
            log.info("name in create: " + name);
            sink.next(1);
        }).publishOn(Schedulers.single()
        ).flatMap(value -> {
            return Mono.deferContextual(contextView -> {
                        var name = contextView.get("name");
                        log.info("name in doOnNext: " + name);
                        return Mono.just(value);
                    });
        }).subscribeOn(Schedulers.parallel()
        ).contextWrite(context -> {
            return context.put("name", "wooman");
        }).subscribe(null, null, null, initialContext);

        Thread.sleep(1000);
        log.info("end main");
    }
}
