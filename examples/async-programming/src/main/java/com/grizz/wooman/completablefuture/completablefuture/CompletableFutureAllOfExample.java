package com.grizz.wooman.completablefuture.completablefuture;

import com.grizz.wooman.completablefuture.completionstage.Helper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class CompletableFutureAllOfExample {
    public static void main(String[] args)
            throws InterruptedException {
        var startTime = System.currentTimeMillis();
        var firstFuture = Helper.waitAndReturn(100, 1);
        var secondFuture = Helper.waitAndReturn(500, 2);
        var thirdFuture = Helper.waitAndReturn(1000, 3);

        CompletableFuture.allOf(firstFuture, secondFuture, thirdFuture)
                .thenAcceptAsync(v -> {
                    log.info("after allOf");
                    try {
                        log.info("first: {}", firstFuture.get());
                        log.info("second: {}", secondFuture.get());
                        log.info("third: {}", thirdFuture.get());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).join();

        var endTime = System.currentTimeMillis();
        log.info("elapsed: {}ms", endTime - startTime);
    }
}
