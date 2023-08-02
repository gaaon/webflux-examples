package com.grizz.wooman.test.condition;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestOnMacExampleTest {
    @TestOnMac
    void test1() {
        assertTrue(true);
    }
}
