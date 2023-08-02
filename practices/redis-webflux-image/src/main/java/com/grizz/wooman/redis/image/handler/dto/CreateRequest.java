package com.grizz.wooman.redis.image.handler.dto;

import lombok.Data;

@Data
public class CreateRequest {
    private String id;
    private String name;
    private String url;
}
