package com.grizz.wooman.test.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Slf4j
public class NonNestedExampleTest {
    @Test
    void test1() {
        log.info("test1");
    }

    class Nested1 {
        @Test
        void test2() {
            log.info("test2");
        }
    }
}
