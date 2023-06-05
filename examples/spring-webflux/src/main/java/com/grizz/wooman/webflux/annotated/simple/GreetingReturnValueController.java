package com.grizz.wooman.webflux.annotated.simple;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@RequestMapping("/return-value")
@Controller
public class GreetingReturnValueController {
    @ResponseBody
    @GetMapping("/future")
    CompletableFuture<String> helloAsFuture() {
        return CompletableFuture.completedFuture("hello world");
    }

    @GetMapping("/void-shr")
    Mono<Void> monoVoid(ServerHttpResponse serverHttpResponse) {
        return serverHttpResponse.writeWith(
                Mono.just(serverHttpResponse.bufferFactory()
                        .wrap("hello world".getBytes()))
        );
    }

    @GetMapping("/void-swe")
    Mono<Void> monoVoidEx(ServerWebExchange serverWebExchange) {
        return serverWebExchange.getResponse().setComplete();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/void-status")
    Void voidStatus() {
        return null;
    }

    @ResponseBody
    @GetMapping("/void")
    Void voidEmptyResp() {
        return null;
    }
}
