package com.grizz.wooman.webflux.functional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class GreetingHandler {
    public static Mono<ServerResponse> greetQueryParam(ServerRequest serverRequest) {
        String name = serverRequest.queryParam("name")
                .orElse("world");

        String content = "Hello " + name;
        return ServerResponse.ok().bodyValue(content);
    }

    public static Mono<ServerResponse> greetPathVariable(ServerRequest serverRequest) {
        String name = serverRequest.pathVariable("name");

        String content = "Hello " + name;
        return ServerResponse.ok().bodyValue(content);
    }

    public static Mono<ServerResponse> greetJsonBody(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(NameHolder.class)
                .map(NameHolder::getName)
                .map(name -> "Hello " + name)
                .flatMap(content -> ServerResponse.ok().bodyValue(content))
                .doOnError(throwable -> log.error("error", throwable));
    }

    public static Mono<ServerResponse> greetPlainTextBody(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(String.class)
                .map(name -> "Hello " + name)
                .flatMap(content -> ServerResponse.ok().bodyValue(content));
    }

    public static Mono<ServerResponse> greetHeader(ServerRequest serverRequest) {
        String name = serverRequest.headers().header("X-Custom-Name")
                .stream()
                .findFirst()
                .orElse("world");

        String content = "Hello " + name;
        return ServerResponse.ok().bodyValue(content);
    }
}
