package com.grizz.wooman.test;

import com.grizz.wooman.test.app.service.GreetingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReactiveBlockingTest {
    private GreetingService greetingService;

    @BeforeEach
    void setup() {
        greetingService = new GreetingService();
    }

    @Test
    void block() {
        // given
        var who = "world";

        // when
        String greeting = greetingService.greetingMono(who)
                .block();

        // then
        assertEquals(greeting, "hello world");
    }
}
