package com.grizz.wooman.test.condition;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Slf4j
public class TagExampleTest {
    @Tag("tag1")
    @Test
    void test1() {
        log.info("test1");
    }

    @Tag("tag2")
    @Test
    void test2() {
        log.info("test2");
    }

    @Tag("tag1")
    @Test
    void test3() {
        log.info("test3");
    }

    @Tag("tag1")
    @Nested
    class Nested1 {
        @Test
        void test4() {
            log.info("test4");
        }
    }
}
