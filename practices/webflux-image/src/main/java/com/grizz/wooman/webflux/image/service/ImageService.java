package com.grizz.wooman.webflux.image.service;

import com.grizz.wooman.webflux.image.entity.common.Image;
import com.grizz.wooman.webflux.image.repository.ImageReactorRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ImageService {
    private ImageReactorRepository imageRepository = new ImageReactorRepository();

    public Mono<Image> getImageById(String imageId) {
        return imageRepository.findById(imageId)
                .map(imageEntity ->
                        new Image(
                                imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl()
                        )
                );
    }
}
