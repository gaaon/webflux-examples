package com.grizz.wooman.webflux.image.entity.common.repository;

import lombok.Data;

@Data
public class ImageEntity {
    private final String id;
    private final String name;
    private final String url;
}
