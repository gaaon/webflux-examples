package com.grizz.wooman.webflux.annotated.simple;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

@Component
@RequestMapping(path = "/greet2", produces = "application/json")
public class GreetingComponent {
    @ResponseBody
    @GetMapping
    Mono<String> greetQueryParam(@RequestParam String name) {
        String content = "Hello " + name;

        return Mono.just(content);
    }
}
