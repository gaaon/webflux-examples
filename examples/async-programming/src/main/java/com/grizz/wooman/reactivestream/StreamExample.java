package com.grizz.wooman.reactivestream;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class StreamExample {
    public static void main(String[] args) {
        var user = getUser().findFirst().get();
        log.info("user: {}", user);
    }

    // get user
    private static Stream<User> getUser() {
        var image = getImage().findFirst().get();
        var articles = getArticles().collect(Collectors.toList());

        return Stream.of(new User(image, articles));
    }

    // get image
    private static Stream<Image> getImage() {
        return Stream.of(new Image("image", "http://image.com"));
    }

    // get articles
    private static Stream<Article> getArticles() {
        return Stream.of(
                new Article("title1", "content1"),
                new Article("title2", "content2"),
                new Article("title3", "content3")
        );
    }
}
