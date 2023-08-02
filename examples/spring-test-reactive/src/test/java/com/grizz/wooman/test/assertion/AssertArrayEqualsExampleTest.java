package com.grizz.wooman.test.assertion;

import com.grizz.wooman.test.Greeting;
import com.grizz.wooman.test.TestToFail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class AssertArrayEqualsExampleTest {
    @Test
    void test1() {
        int[] expected = {1, 2, 3};
        int[] actual = {1, 2, 3};
        assertArrayEquals(expected, actual);
    }

    @TestToFail
    void test2() {
        Object[] expected = {new Greeting("hello")};
        Object[] actual = {new Greeting("hello")};
        assertArrayEquals(expected, actual);
    }
}
