package com.grizz.wooman.test.assertion;

import com.grizz.wooman.test.Greeting;
import com.grizz.wooman.test.GreetingWithEquals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class AssertSameExampleTest {
    @Test
    void test1() {
        var expected = new GreetingWithEquals("hello");
        var actual = new GreetingWithEquals("hello");
        assertNotSame(expected, actual);
    }

    @Test
    void test2() {
        Greeting expected = new Greeting("hello");
        Greeting actual = expected;
        assertSame(expected, actual);
    }
}
