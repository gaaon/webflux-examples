package com.grizz.wooman.reactivestream;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SyncCallExample {
    public static void main(String[] args) {
        var user = getUser();
        log.info("user: {}", user);
    }

    // get user
    private static User getUser() {
        var image = getImage();
        var articles = getArticles();

        return new User(image, articles);
    }

    // get image
    private static Image getImage() {
        return new Image("image", "http://image.com");
    }

    // get articles
    private static List<Article> getArticles() {
        return List.of(
                new Article("title1", "content1"),
                new Article("title2", "content2"),
                new Article("title3", "content3")
        );
    }
}
