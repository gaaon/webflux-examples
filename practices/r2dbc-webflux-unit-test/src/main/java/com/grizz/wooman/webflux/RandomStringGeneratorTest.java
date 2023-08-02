package com.grizz.wooman.webflux;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RandomStringGeneratorTest {
    public static void main(String[] args) {
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            char item = (char)('A' + (Math.random() * 26));
            token.append(item);
        }

        log.info(token.toString());
    }
}
