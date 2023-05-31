package com.grizz.wooman.completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

@Slf4j
public class CompletionStageThenAcceptAsyncRunningExample {
    public static void main(String[] args)
            throws InterruptedException {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.runningStage();
        stage.thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync", i);
        }).thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync", i);
        });

        Thread.sleep(2000);
    }
}
