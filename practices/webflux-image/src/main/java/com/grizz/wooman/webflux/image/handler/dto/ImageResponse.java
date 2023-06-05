package com.grizz.wooman.webflux.image.handler.dto;

import lombok.Data;

@Data
public class ImageResponse {
    private final String id;
    private final String name;
    private final String url;
}