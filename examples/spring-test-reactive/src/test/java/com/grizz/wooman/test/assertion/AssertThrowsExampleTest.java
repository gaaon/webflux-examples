package com.grizz.wooman.test.assertion;

import com.grizz.wooman.test.TestToFail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertThrowsExampleTest {
    @Test
    void test1() {
        assertThrows(
                IllegalStateException.class,
                () -> { throw new IllegalStateException(); }
        );
    }

    @Test
    void test2() {
        assertThrows(
                RuntimeException.class,
                () -> { throw new IllegalStateException(); }
        );
    }

    @TestToFail
    void test3() {
        assertThrows(
                IllegalStateException.class,
                () -> { throw new RuntimeException(); }
        );
    }
}
