package com.grizz.wooman.webflux;

import com.grizz.wooman.webflux.common.Image;
import com.grizz.wooman.webflux.common.User;
import com.grizz.wooman.webflux.common.repository.UserEntity;

import java.util.Collections;
import java.util.Optional;

public class TestDataBuilder {
    public static User createUser(String id) {
        var profileImage = new Image(
                "1",
                "profile",
                "https://grizz.kim/images/1"
        );

        return new User(
                id,
                "grizz",
                20,
                Optional.of(profileImage),
                Collections.emptyList(),
                100L
        );
    }

    public static User createUser(
            String id,
            String name,
            Integer age,
            String password,
            String profileImageId
    ) {
        var profileImage = new Image(
                profileImageId,
                "profile",
                "https://grizz.kim/images/1"
        );

        return new User(
                id,
                name,
                age,
                Optional.of(profileImage),
                Collections.emptyList(),
                100L
        );
    }

    public static UserEntity createUnsavedUserEntity(
            String name
    ) {
        return new UserEntity(
                name,
                20,
                "1",
                "password"
        );
    }

}
