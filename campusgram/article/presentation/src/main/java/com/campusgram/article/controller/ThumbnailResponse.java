package com.campusgram.article.controller;

import lombok.Data;

@Data
public class ThumbnailResponse {
    private final String id;
    private final String url;
    private final int width;
    private final int height;
}
