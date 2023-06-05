package com.grizz.wooman.webflux;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.util.function.Function;

@Slf4j
public class WebExceptionHandlerMultipleExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        var webHandler = new WebHandler() {
            @Override
            public Mono<Void> handle(ServerWebExchange exchange) {
                final ServerHttpResponse response = exchange.getResponse();

                return response.writeWith(
                        Mono.create(sink -> {
                                    sink.error(new CustomException("custom exception"));
                                }
                        ));
            }
        };

        Function<String, WebExceptionHandler> exceptionHandlerFactory = (body) ->
                (WebExceptionHandler) (exchange, ex) -> {
                    final ServerHttpResponse response = exchange.getResponse();

                    if (ex instanceof CustomException) {
                        response.setStatusCode(HttpStatus.BAD_REQUEST);
                        var respBody = response.bufferFactory().wrap(body.getBytes());

                        return response.writeWith(Mono.just(respBody));
                    } else {
                        return Mono.error(ex);
                    }
                };

        final HttpHandler webHttpHandler = WebHttpHandlerBuilder
                .webHandler(webHandler)
                .exceptionHandler(
                        exceptionHandlerFactory.apply("3"),
                        exceptionHandlerFactory.apply("2"),
                        exceptionHandlerFactory.apply("1")
                )
                .build();

        final var adapter = new ReactorHttpHandlerAdapter(webHttpHandler);
        HttpServer.create()
                .host("localhost")
                .port(8080)
                .handle(adapter)
                .bindNow()
                .channel().closeFuture().sync();
    }
}
