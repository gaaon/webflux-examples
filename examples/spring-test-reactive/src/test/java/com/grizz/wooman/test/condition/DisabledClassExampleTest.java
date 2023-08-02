package com.grizz.wooman.test.condition;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("테스트 클래스 자체를 ignore")
public class DisabledClassExampleTest {
    @Test
    void test1() {
        assertEquals(1, 1);
    }

    @Test
    void test2() {
        assertEquals(2, 2);
    }

    @Nested
    class NestedTest {
        @Test
        void test3() {
            assertEquals(3, 3);
        }
    }
}
