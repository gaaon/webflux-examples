package com.grizz.wooman.webflux.webhandler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
    @GetMapping("/test")
    public Mono<String> hello() {
        return Mono.just("Hello");
    }
}
