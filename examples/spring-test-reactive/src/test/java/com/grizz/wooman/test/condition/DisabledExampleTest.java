package com.grizz.wooman.test.condition;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisabledExampleTest {
    @Test
    @Disabled
    void test1() {
        assertEquals(1, 1);
    }

    @Test
    @Disabled("#42 버그가 고쳐질 때까지 비활성화")
    void test2() {
        assertEquals(2, 2);
    }
}
