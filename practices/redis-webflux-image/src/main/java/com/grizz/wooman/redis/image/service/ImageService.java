package com.grizz.wooman.redis.image.service;

import com.grizz.wooman.redis.image.common.Image;
import com.grizz.wooman.redis.image.common.repository.ImageEntity;
import com.grizz.wooman.redis.image.repository.ImageReactorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageReactorRepository imageRepository;

    public Mono<Image> getImageById(String imageId) {
        return imageRepository.findById(imageId)
                .map(this::map);
    }

    public Mono<Image> createImage(String id, String name, String url) {
        return imageRepository.create(id, name, url)
                .map(this::map);
    }

    private Image map(ImageEntity imageEntity) {
        return new Image(
                imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl()
        );
    }
}
