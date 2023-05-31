package com.grizz.wooman.completablefuture.result.future.repository;

import lombok.SneakyThrows;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class FollowFutureRepository {
    private Map<String, Long> userFollowCountMap;

    public FollowFutureRepository() {
        userFollowCountMap = Map.of("1234", 1000L);
    }

    @SneakyThrows
    public CompletableFuture<Long> countByUserId(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return userFollowCountMap.getOrDefault(userId, 0L);
        });
    }
}
