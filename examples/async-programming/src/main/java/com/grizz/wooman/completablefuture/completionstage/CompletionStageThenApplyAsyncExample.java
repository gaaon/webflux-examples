package com.grizz.wooman.completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

@Slf4j
public class CompletionStageThenApplyAsyncExample {
    public static void main(String[] args)
            throws InterruptedException {
        CompletionStage<Integer> stage = Helper.completionStage();
        stage.thenApplyAsync(value -> {
            var next = value + 1;
            log.info("in thenApplyAsync: {}", next);
            return next; // add 1
        }).thenApplyAsync(value -> {
            var next = "result: " + value;
            log.info("in thenApplyAsync2: {}", next);
            return next;
        }).thenApplyAsync(value -> {
            var next = value.equals("result: 2");
            log.info("in thenApplyAsync3: {}", next);
            return next;
        }).thenAcceptAsync(value -> log.info("{}", value));

        Thread.sleep(100);
    }
}
