package com.grizz.wooman.test.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class BeforeAfterEachMultiExampleTest {
    void beforeEach1() { log.info("beforeEach1"); }
    void beforeEach2() { log.info("beforeEach2"); }
    void beforeEach3() { log.info("beforeEach3"); }

    @BeforeEach
    void beforeEach() {
        beforeEach1();
        beforeEach2();
        beforeEach3();
    }

    @Test
    void test1() {
        log.info("test1");
        assertTrue(true);
    }
}
