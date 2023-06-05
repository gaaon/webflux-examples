package com.grizz.wooman.webflux.common.repository;

import lombok.Data;

@Data
public class UserEntity {
    private final String id;
    private final String name;
    private final int age;
    private final String profileImageId;
    private final String password;
}
