package com.grizz.wooman.webflux.annotated.simple;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greet/consume")
public class GreetingConsumeController {
    @NoArgsConstructor
    private static class NameHolder {
        @Getter
        @Setter
        private String name;
    }

    @PostMapping(value = "/1", consumes = "application/json")
    String greetJsonBody(@RequestBody NameHolder nameHolder) {
        return "Hello " + nameHolder.getName();
    }

    @PostMapping(value = "/2", consumes = "!application/json")
    String greetNoJson() {
        return "Hello no json";
    }
}
