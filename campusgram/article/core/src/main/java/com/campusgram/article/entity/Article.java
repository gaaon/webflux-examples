package com.campusgram.article.entity;

import lombok.Data;

import java.util.List;

@Data
public class Article {
    private final String id;
    private final String title;
    private final String content;
    private final List<ArticleThumbnail> thumbnails;
    private final String creatorId;

    public static Article create(
            String title,
            String content,
            List<ArticleThumbnail> thumbnails,
            String creatorId
    ) {
        return new Article(null, title, content, thumbnails, creatorId);
    }
}
