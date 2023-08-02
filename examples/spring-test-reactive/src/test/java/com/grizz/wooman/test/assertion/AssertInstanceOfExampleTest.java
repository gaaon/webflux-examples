package com.grizz.wooman.test.assertion;

import com.grizz.wooman.test.Greeting;
import com.grizz.wooman.test.GreetingWithEquals;
import com.grizz.wooman.test.TestToFail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class AssertInstanceOfExampleTest {
    @Test
    void test1() {
        Object obj = new Greeting("hello");
        assertInstanceOf(Greeting.class, obj);
    }

    @Test
    void test2() {
        Object obj = new GreetingWithEquals("hello");
        assertInstanceOf(Greeting.class, obj);
    }

    @TestToFail
    void test3() {
        Object obj = new Greeting("hello");
        assertInstanceOf(GreetingWithEquals.class, obj);
    }
}
