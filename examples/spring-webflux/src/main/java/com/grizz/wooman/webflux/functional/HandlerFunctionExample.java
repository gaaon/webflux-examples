package com.grizz.wooman.webflux.functional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseCookie;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
public class HandlerFunctionExample {
    public static void main(String[] args) {
        HandlerFunction<ServerResponse> handler = request -> {
            String name = request.queryParam("name")
                    .orElse("world");

            String content = "Hello, " + name;
            ResponseCookie cookie = ResponseCookie.from("name", name).build();
            return ServerResponse.ok()
                    .cookie(cookie)
                    .headers(headers ->
                            headers.add("X-Hello", name))
                    .cacheControl(CacheControl.noCache())
                    .bodyValue(content);
        };
    }
}
