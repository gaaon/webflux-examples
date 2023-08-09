package com.campusgram.article.common.image;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ImageClient {
    private final WebClient imageWebClient;
    private final ReactiveCircuitBreakerFactory cb;

    public Flux<ImageResponse> getImagesByIds(List<String> imageIds) {
        String param = String.join(",", imageIds);

        var flux = imageWebClient.get()
                .uri("/api/images?imageIds=" + param)
                .retrieve()
                .bodyToFlux(ImageResponse.class);

        return flux.transform(it ->
                cb.create("image").run(it, e ->
                        Flux.create(emitter -> {
                            for (String imageId : imageIds) {
                                var defaultImage = new ImageResponse(
                                        Long.parseLong(imageId),
                                        "http://grizz.com/images/default",
                                        100,
                                        100);
                                emitter.next(defaultImage);
                            }
                            emitter.complete();
                        }))
        );
    }
}
