package com.grizz.wooman.test.assertion;

import com.grizz.wooman.test.TestToFail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class AssertThrowsExactlyExampleTest {
    @Test
    void test1() {
        assertThrowsExactly(
                IllegalStateException.class,
                () -> {
                    throw new IllegalStateException();
                }
        );
    }

    @TestToFail
    void test2() {
        assertThrowsExactly(
                RuntimeException.class,
                () -> {
                    throw new IllegalStateException();
                }
        );
    }
}
