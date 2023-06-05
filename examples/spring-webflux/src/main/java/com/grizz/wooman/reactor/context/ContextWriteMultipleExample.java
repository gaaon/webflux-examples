package com.grizz.wooman.reactor.context;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

@Slf4j
public class ContextWriteMultipleExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");

        Flux.create(sink -> {
            var context = sink.contextView();
            log.info("in create, context: {}", context);
            sink.next(1);
        }).contextWrite(context -> {
            return context.put("name", "wooman"); // 두번째
        }).flatMap(item -> ContextLogger.logContext(item, "in flatMap")
        ).contextWrite(context -> {
            return context.put("name", "taewoo"); // 처음
        }).subscribeOn(Schedulers.single()
        ).subscribe(null, null, null,
                Context.empty().put("name", "init")
        );

        Thread.sleep(1000);
        log.info("end main");
    }
}
