package com.grizz.wooman.webflux.functional.application;

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
                        .nest(accept(MediaType.TEXT_PLAIN), b2 -> b2
                                .GET("/",
                                        queryParam("name", name -> !name.isBlank()),
                                        GreetingHandler::greetQueryParam)
                                .GET("/name/{name}", GreetingHandler::greetPathVariable)
                                .GET("/header",
                                        headers(h -> h.firstHeader("X-Custom-Name") != null),
                                        GreetingHandler::greetHeader)
                                .POST("/json", contentType(MediaType.APPLICATION_JSON),
                                        GreetingHandler::greetJsonBody)
                                .POST("/text", GreetingHandler::greetPlainTextBody)
                        )
                )
                .build();
    }
}
