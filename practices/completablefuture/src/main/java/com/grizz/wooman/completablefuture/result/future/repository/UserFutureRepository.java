package com.grizz.wooman.completablefuture.result.future.repository;

import com.grizz.wooman.completablefuture.result.common.repository.UserEntity;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class UserFutureRepository {
    private static Map<String, UserEntity> userMap;

    public UserFutureRepository() {
        var user = new UserEntity("1234", "taewoo", 32, "image#1000");

        userMap = Map.of("1234", user);
    }

    public CompletableFuture<Optional<UserEntity>> findById(String userId) {
        var user = userMap.get(userId);
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return Optional.ofNullable(user);
        });
    }
}
