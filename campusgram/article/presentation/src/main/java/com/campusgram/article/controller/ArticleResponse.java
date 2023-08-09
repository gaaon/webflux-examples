package com.campusgram.article.controller;

import lombok.Data;

import java.util.List;

@Data
public class ArticleResponse {
    private final String id;
    private final String title;
    private final String content;
    private final String creatorId; // 생성한의 userId
    private final List<ThumbnailResponse> thumbnails;
}
