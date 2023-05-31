package com.grizz.wooman.completablefuture.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CompletableFutureRunAsyncExample {
    public static void main(String[] args)
            throws ExecutionException, InterruptedException {
        log.info("start main");
        var future = CompletableFuture.runAsync(() -> {
            log.info("runAsync");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        assert !future.isDone();

        Thread.sleep(1000);
        assert future.isDone();
        assert future.get() == null;

        log.info("end main");
    }
}
