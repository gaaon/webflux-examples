package com.grizz.wooman.completablefuture.result.blocking.repository;

import lombok.SneakyThrows;

import java.util.Map;

public class FollowRepository {
    private Map<String, Long> userFollowCountMap;

    public FollowRepository() {
        userFollowCountMap = Map.of("1234", 1000L);
    }

    @SneakyThrows
    public Long countByUserId(String userId) {
        Thread.sleep(1000);
        return userFollowCountMap.getOrDefault(userId, 0L);
    }
}
