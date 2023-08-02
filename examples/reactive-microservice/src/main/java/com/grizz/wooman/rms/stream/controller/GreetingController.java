package com.grizz.wooman.rms.stream.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/greeting")
public class GreetingController {
    private final StreamBridge streamBridge;

    @GetMapping
    public void greeting(
            @RequestParam("name") String name
    ) {
        streamBridge.send("addGreeting-in-0", name);
    }
}
