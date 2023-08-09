package com.campusgram.article.common.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageResponse {
    private Long id;
    private String url;
    private int width;
    private int height;
}
