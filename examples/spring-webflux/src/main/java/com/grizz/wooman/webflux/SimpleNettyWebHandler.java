package com.grizz.wooman.webflux;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.server.*;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

@Slf4j
public class SimpleNettyWebHandler {
    @SneakyThrows
    public static void main(String[] args) {
        var webHandler = new WebHandler() {
            @Override
            public Mono<Void> handle(ServerWebExchange exchange) {
                return exchange.getResponse().writeWith(
                        Mono.just(
                                exchange.getResponse().bufferFactory()
                                        .wrap("Hello World".getBytes())
                        )
                );
            }
        };

        var webExceptionHandler = new WebExceptionHandler() {
            @Override
            public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
                log.info("exception: {}", ex.getMessage());
                var buffer = exchange.getResponse().bufferFactory().wrap("error".getBytes());
                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                return exchange.getResponse().writeWith(Mono.just(buffer));
            }
        };

        var webFilter = new WebFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                log.info("filter");
                return chain.filter(exchange)
                        .doOnSuccess(v -> {
                            log.info("doOnSuccess");
                        });
            }
        };

        var httpHandler = WebHttpHandlerBuilder.webHandler(webHandler)
                .filter(webFilter)
                .exceptionHandler(webExceptionHandler)
                .build();

        var adapter = new ReactorHttpHandlerAdapter(httpHandler);

        HttpServer.create()
                .host("localhost")
                .port(8080)
                .handle(adapter)
                .bindNow()
                .channel().closeFuture().sync();
    }
}
