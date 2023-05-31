package com.grizz.wooman.completablefuture.result.future.repository;

import com.grizz.wooman.completablefuture.result.common.repository.ImageEntity;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ImageFutureRepository {
    private static Map<String, ImageEntity> imageMap;

    public ImageFutureRepository() {
        imageMap = Map.of(
                "image#1000", new ImageEntity("image#1000", "profileImage", "https://dailyone.com/images/1000")
        );
    }

    public CompletableFuture<Optional<ImageEntity>> findById(String id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return Optional.ofNullable(imageMap.get(id));
        });
    }
}
