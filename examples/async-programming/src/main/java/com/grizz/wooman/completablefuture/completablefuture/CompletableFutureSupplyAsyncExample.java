package com.grizz.wooman.completablefuture.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CompletableFutureSupplyAsyncExample {
    public static void main(String[] args)
            throws ExecutionException, InterruptedException {
        log.info("start main");
        var future = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        });
        assert !future.isDone();

        Thread.sleep(1000);

        assert future.isDone();
        assert future.get() == 1;

        log.info("end main");
    }
}
