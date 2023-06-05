package com.grizz.wooman.webflux.annotated.simple;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@RequestMapping("/greet/rendering")
@Controller
public class GreetingRenderingController {
    @GetMapping("/redirect")
    Mono<Rendering> redirect() {
        var rendering = Rendering.redirectTo("/test")
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .header("X-Custom-Name", "taewoo")
                .contextRelative(true)
                .propagateQuery(true)
                .build();

        return Mono.just(rendering);
    }

    @GetMapping("/redirect2")
    String redirect2() {
        return "redirect:/test2";
    }

    @GetMapping("/redirect3")
    RedirectView redirect3() {
        return new RedirectView("/test3");
    }

    @GetMapping("/hello-html")
    Mono<Rendering> helloHtml(@RequestParam String name) {
        var rendering = Rendering.view("hello")
                .modelAttribute("name", name)
                .status(HttpStatus.CREATED)
                .header("X-Custom-Name", name)
                .build();

        return Mono.just(rendering);
    }
}
