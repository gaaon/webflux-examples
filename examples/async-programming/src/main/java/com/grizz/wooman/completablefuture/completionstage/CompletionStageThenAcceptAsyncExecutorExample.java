package com.grizz.wooman.completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;

@Slf4j
public class CompletionStageThenAcceptAsyncExecutorExample {
    public static void main(String[] args)
            throws InterruptedException {

        var single = Executors.newSingleThreadExecutor();
        var fixed = Executors.newFixedThreadPool(10);

        log.info("start main");
        CompletionStage<Integer> stage = Helper.completionStage();
        stage.thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync", i);
        }, fixed).thenAcceptAsync(i -> {
            log.info("{} in thenAcceptAsync2", i);
        }, single);
        log.info("after thenAccept");
        Thread.sleep(200);

        single.shutdown();
        fixed.shutdown();
    }
}
