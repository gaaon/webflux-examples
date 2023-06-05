package com.grizz.wooman.webflux.annotated.simple;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(path = "/greet")
public class GreetingController {
    @ResponseBody
    @GetMapping(path = "/query", params = "name", produces = "text/plain")
    Mono<String> greetQueryParam(@RequestParam String name) {
        String content = "Hello " + name;
        return Mono.just(content);
    }

    @ResponseBody
    @GetMapping("/na?e/*/{name:[a-zA-Z]+}/{place}")
    Mono<String> greetPathVariable(
            @PathVariable String name,
            @PathVariable String place
    ) {
        String content = "Hello " + name + " in " + place;
        return Mono.just(content);
    }
}
