package com.grizz.wooman.completablefuture.result.common.repository;

import lombok.Data;

@Data
public class ArticleEntity {
    private final String id;
    private final String title;
    private final String content;
    private final String userId;
}
