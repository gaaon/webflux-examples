package com.grizz.wooman.test.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@Slf4j
public class BeforeAfterAllExampleTest {
    @BeforeAll
    static void beforeAll() {
        log.info("beforeAll");
    }

    @Test
    void test1() {
        log.info("test1");
    }

    @Test
    void test2() {
        log.info("test2");
    }

    @Test
    void test3() {
        log.info("test3");
    }

    @AfterAll
    static void afterAll() {
        log.info("afterAll");
    }
}
