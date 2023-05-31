package com.grizz.wooman.completablefuture.completionstage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletionStageExample {
    public static void main(String[] args) throws InterruptedException {
        Helper.completionStage()
                .thenApplyAsync(value -> {
                    log.info("thenApplyAsync: {}", value);
                    return value + 1;
                }).thenAccept(value -> {
                    log.info("thenAccept: {}", value);
                }).thenRunAsync(() -> {
                    log.info("thenRun");
                }).exceptionally(e -> {
                    log.info("exceptionally: {}", e.getMessage());
                    return null;
                });

        Thread.sleep(100);
    }
}
