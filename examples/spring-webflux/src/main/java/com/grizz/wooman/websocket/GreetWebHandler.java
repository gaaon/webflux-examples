package com.grizz.wooman.websocket;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class GreetWebHandler implements WebHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange) {
        String name = exchange.getRequest().getQueryParams()
                .getFirst("name");

        if (name == null) name = "world";

        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(("Hello " + name)
                                .getBytes(StandardCharsets.UTF_8))
                )
        );
    }
}
