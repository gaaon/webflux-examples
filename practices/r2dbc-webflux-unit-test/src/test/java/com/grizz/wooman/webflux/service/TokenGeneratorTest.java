package com.grizz.wooman.webflux.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

public class TokenGeneratorTest {
    TokenGenerator tokenGenerator;

    @BeforeEach
    void setup() {
        tokenGenerator = new TokenGenerator();
    }

    @Test
    void when_call_then_return_token() {
        // when
        String result = tokenGenerator.execute();

        // then
        assertLinesMatch(List.of("^[A-Z]{6}$"), List.of(result));
    }
}
