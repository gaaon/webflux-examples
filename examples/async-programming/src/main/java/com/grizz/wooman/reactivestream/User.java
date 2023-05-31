package com.grizz.wooman.reactivestream;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@RequiredArgsConstructor
public class User {
    private final Image image;
    private final List<Article> articles;
}
