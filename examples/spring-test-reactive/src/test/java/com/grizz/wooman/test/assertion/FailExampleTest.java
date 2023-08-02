package com.grizz.wooman.test.assertion;

import com.grizz.wooman.test.TestToFail;

import static org.junit.jupiter.api.Assertions.fail;

public class FailExampleTest {
    @TestToFail
    void test1() {
        var hasProblem = true;
        if (hasProblem) {
            fail();
        }
    }

    @TestToFail
    void test2() {
        var cause = new IllegalStateException();
        fail(cause);
    }
}
