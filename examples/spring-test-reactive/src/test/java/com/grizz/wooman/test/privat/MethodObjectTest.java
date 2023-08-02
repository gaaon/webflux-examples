package com.grizz.wooman.test.privat;

import com.grizz.wooman.test.GreetingGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodObjectTest {
    @Test
    void test1() {
        String who = "world";
        GreetingGenerator greetingGenerator =
                new GreetingGenerator(who);

        String expected = "hello " + who;
        assertEquals(expected, greetingGenerator.execute());
    }
}
