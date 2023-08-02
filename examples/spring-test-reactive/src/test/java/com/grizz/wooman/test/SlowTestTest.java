package com.grizz.wooman.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SlowTestTest {
    @SlowTest
    void test() {
        assertEquals(1, 1);
    }
}
