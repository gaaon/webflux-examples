package com.grizz.wooman.webflux;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

@Slf4j
public class WebFilterExample {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        var webHandler = new WebHandler() {
            @Override
            public Mono<Void> handle(ServerWebExchange exchange) {
                log.info("web handler");
                final ServerHttpRequest request = exchange.getRequest();
                final ServerHttpResponse response = exchange.getResponse();

                final String nameHeader = request.getHeaders()
                        .getFirst("X-Custom-Name");
                log.info("X-Custom-Name: {}", nameHeader);
                String name = exchange.getAttribute("name");
                String content = "Hello " + name;
                Mono<DataBuffer> responseBody = Mono.just(
                        response.bufferFactory().wrap(content.getBytes())
                );

                response.getHeaders()
                        .add("Content-Type", "text/plain");
                return response.writeWith(responseBody);
            }
        };

        var extractNameFromHeaderFilter = new WebFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                log.info("extractNameFromHeaderFilter");
                final ServerHttpRequest request = exchange.getRequest();
                final ServerHttpResponse response = exchange.getResponse();

                String name = request.getHeaders()
                        .getFirst("X-Custom-Name");

                if (name == null) {
                    response.setStatusCode(HttpStatus.BAD_REQUEST);
                    return response.setComplete();
                } else {
                    exchange.getAttributes().put("name", name);
                    var newReq = request.mutate()
                            .headers(h -> h.remove("X-Custom-Name"))
                            .build();
                    return chain.filter(
                            exchange.mutate().request(newReq).build()
                    );
                }
            }
        };

        var timeLoggingFilter = new WebFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                log.info("timeLoggingFilter");
                long startTime = System.nanoTime();
                return chain.filter(exchange)
                        .doOnSuccess(v -> {
                            long endTime = System.nanoTime();
                            log.info("time: {} ms", (endTime - startTime)/1000000.0);
                        });
            }
        };

        final HttpHandler webHttpHandler = WebHttpHandlerBuilder
                .webHandler(webHandler)
                .filter(
                        extractNameFromHeaderFilter,
                        timeLoggingFilter
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
