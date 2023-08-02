package com.grizz.wooman.redis.image.common;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class User {
    private final String id;
    private final String name;
    private final int age;
    private final Optional<Image> profileImage;
    private final List<Article> articleList;
    private final Long followCount;
}
