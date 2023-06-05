package com.grizz.wooman.reactor.scheduler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduledExecutorServiceExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        var executor = Executors.newScheduledThreadPool(3);
        executor.execute(() -> {
            log.info("immediate hello");
        });

        executor.schedule(() -> {
            log.info("delayed hello");
        }, 4, TimeUnit.SECONDS);

        executor.scheduleWithFixedDelay(() -> {
            log.info("repeated hello");
        }, 2000, 500, TimeUnit.MILLISECONDS);

        log.info("end main");
        Thread.sleep(5000);
        executor.shutdown();
    }
}
