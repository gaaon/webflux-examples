package com.grizz.wooman.test.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
public class BeforeEachAllExampleTest {
    @BeforeAll
    static void beforeAll() { log.info("beforeAll"); }

    @BeforeEach
    void beforeEach() { log.info("beforeEach"); }

    @Test
    void test1() { log.info("test1"); }

    @Test
    void test2() { log.info("test2"); }

    @AfterEach
    void afterEach() { log.info("afterEach"); }

    @AfterAll
    static void afterAll() { log.info("afterAll"); }
}
