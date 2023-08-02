package com.grizz.wooman.test.assertion;

import com.grizz.wooman.test.Greeting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AssertEqualsOrNotExampleTest {
    @Test
    void checkEquals() {
        assertEquals(1, 1);
    }

    @Test
    void checkNotEquals() {
        assertNotEquals(1, 2);
    }

    @Test
    void checkObjectEquals() {
        Assertions.assertNotEquals(
                new Greeting("hello"), new Greeting("hello")
        );
    }
}
