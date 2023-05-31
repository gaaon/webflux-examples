package com.grizz.wooman.completablefuture.result.blocking.repository;

import com.grizz.wooman.completablefuture.result.common.repository.UserEntity;
import lombok.SneakyThrows;

import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private static Map<String, UserEntity> userMap;

    public UserRepository() {
        var user = new UserEntity("1234", "taewoo", 32, "image#1000");

        userMap = Map.of("1234", user);
    }

    @SneakyThrows
    public Optional<UserEntity> findById(String userId) {
        Thread.sleep(1000);
        var user = userMap.get(userId);
        return Optional.ofNullable(user);
    }


}
