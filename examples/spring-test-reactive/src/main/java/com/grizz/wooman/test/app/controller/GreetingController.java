package com.grizz.wooman.test.app.controller;

import com.grizz.wooman.test.app.service.GreetingService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/greeting")
public class GreetingController {
    private final GreetingService greetingService;

    @GetMapping
    public Mono<String> greeting(
            @RequestParam String who
    ) {
        return greetingService.greetingMono(who);
    }

    @GetMapping("/header")
    public Mono<ResponseEntity<String>> greetingWithHeader(
            @RequestParam String who,
            @RequestParam Long age
    ) {
        return greetingService.greetingMono(who)
                .map(message -> ResponseEntity.ok()
                        .header("X-WHO", who)
                        .header("X-AGE", age.toString())
                        .body(message));
    }

    @GetMapping("/cookie")
    public Mono<String> greetingWithCookie(
            @RequestParam String who,
            ServerWebExchange exchange
    ) {
        var cookie = ResponseCookie.from("who", who)
                .maxAge(3600)
                .domain("grizz.kim")
                .httpOnly(true)
                .path("/")
                .sameSite("None")
                .secure(true)
                .build();

        exchange.getResponse().addCookie(cookie);

        return greetingService.greetingMono(who);
    }

    @Data
    static class GreetingResponse {
        private final String message;
        private final Long age;
        private final String who;
    }

    @GetMapping("/body")
    public Mono<GreetingResponse> greetingWithBody(
            @RequestParam String who,
            @RequestParam Long age
    ) {
        return greetingService.greetingMono(who)
                .map(message ->
                        new GreetingResponse(message, age, who));
    }
}
