package com.grizz.wooman.reactivestream;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class FutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var user = getUser().get();
        log.info("user: {}", user);
    }

    // get user
    private static Future<User> getUser() {
        var imageFuture = getImage();
        var articlesFuture = getArticles();

        return CompletableFuture.allOf(imageFuture, articlesFuture)
                .thenApply(u -> {
                    try {
                        return new User(imageFuture.get(), articlesFuture.get());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    // get image
    private static CompletableFuture<Image> getImage() {
        return CompletableFuture.completedFuture(new Image("image", "http://image.com"));
    }

    // get articles
    private static CompletableFuture<List<Article>> getArticles() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return List.of(
                    new Article("title1", "content1"),
                    new Article("title2", "content2"),
                    new Article("title3", "content3")
            );
        });
    }
}
