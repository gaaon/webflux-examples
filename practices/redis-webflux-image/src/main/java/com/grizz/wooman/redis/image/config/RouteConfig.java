package com.grizz.wooman.redis.image.config;

import com.grizz.wooman.redis.image.handler.ImageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouteConfig {
    @Bean
    RouterFunction router(
            ImageHandler imageHandler
    ) {
        return route()
                .path("/api", b1 -> b1
                        .path("/images", b2 -> b2
                                .GET("/{imageId:[0-9]+}", imageHandler::getImageById)
                                .POST(imageHandler::addImage)
                        )
                ).build();
    }
}
