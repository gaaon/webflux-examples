package com.campusgram.article.entity;

import lombok.Data;

@Data
public class ArticleThumbnail {
    private final String id;
    private final String url;
    private final int width;
    private final int height;
}
