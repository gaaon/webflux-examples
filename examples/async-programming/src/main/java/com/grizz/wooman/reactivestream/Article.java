package com.grizz.wooman.reactivestream;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class Article {
    private final String title;
    private final String content;
}
