package com.grizz.wooman.completablefuture.result.blocking.repository;

import com.grizz.wooman.completablefuture.result.common.repository.ImageEntity;
import lombok.SneakyThrows;

import java.util.Map;
import java.util.Optional;

public class ImageRepository {
    private static Map<String, ImageEntity> imageMap;

    public ImageRepository() {
        imageMap = Map.of(
                "image#1000", new ImageEntity("image#1000", "profileImage", "https://dailyone.com/images/1000")
        );
    }

    @SneakyThrows
    public Optional<ImageEntity> findById(String id) {
        Thread.sleep(1000);
        return Optional.ofNullable(imageMap.get(id));
    }
}
