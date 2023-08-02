package com.grizz.wooman.test.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class BeforeAfterEachExampleTest {
    @BeforeEach
    void beforeEach() {
        log.info("beforeEach");
    }

    @Test
    void test1() {
        log.info("test1");
        assertTrue(true);
    }

    @Test
    void test2() {
        log.info("test2");
        assertTrue(true);
    }

    @AfterEach
    void afterEach() {
        log.info("afterEach");
    }
}
