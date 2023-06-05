package com.grizz.wooman.webflux;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRoutes;

import java.util.function.Consumer;

@Slf4j
public class ReactorNettyHttpExample {
    public static void main(String[] args) {
        log.info("start main");
        Consumer<HttpServerRoutes> routesConsumer = routes ->
                routes.get("/hello", (request, response) -> {
                            var data = Mono.just("Hello World!");
                            return response.sendString(data);
                        }
                );

        HttpServer.create()
                .route(routesConsumer)
                .port(8080)
                .bindNow()
                .onDispose()
                .block();
        log.info("end main");
    }
}
