package com.grizz.wooman.completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

@Slf4j
public class CompletionStageThenAcceptComplexExample {
    public static void main(String[] args) throws InterruptedException {
        log.info("start main");
        CompletionStage<Integer> stage = Helper.completionStageAfter1s();
        stage.thenAccept(i -> {
            log.info("in thenAccept");
        });

        CompletionStage<Integer> stage2 = Helper.completionStageAfter1s();
        Thread.sleep(2000);
        stage2.thenAccept(i -> {
            log.info("in thenAccept after 2s");
        });

        CompletionStage<Integer> stage3 = Helper.completionStageAfter1s();
        stage3.thenAcceptAsync(i -> {
            log.info("in thenAcceptAsync");
        });

        CompletionStage<Integer> stage4 = Helper.completionStageAfter1s();
        Thread.sleep(2000);
        stage4.thenAcceptAsync(i -> {
            log.info("in thenAcceptAsync after 2s");
        });
        Thread.sleep(1000);
        log.info("finish main");
    }
}
