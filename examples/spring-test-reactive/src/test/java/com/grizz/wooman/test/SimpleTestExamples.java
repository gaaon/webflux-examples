package com.grizz.wooman.test;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.abort;

class SimpleTestExamples {
    @Test
    void test1() {
        assertTrue(true);
    }

    @Test
    void test2() {
        fail("fail");
    }

    @Test
    void test3() {
        abort("abort");
    }
}
