package com.grizz.wooman.webflux.annotated.simple;

import com.grizz.wooman.webflux.functional.GreetingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> router() {
        return route()
                .path("/greet", b1 -> b1
                        .POST("/json", contentType(MediaType.APPLICATION_JSON),
                                GreetingHandler::greetJsonBody)
                )
                .build();
    }
}
