package com.grizz.wooman.rms.circuitbreaker;

import org.springframework.stereotype.Component;

@Component
public class Greeter {
    public String generate(String who) {
        return "Hello " + who + "!";
    }
}
