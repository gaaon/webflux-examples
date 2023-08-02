package com.grizz.wooman.test.assume;

import com.grizz.wooman.test.TestToIgnore;

import static org.junit.jupiter.api.Assumptions.abort;

public class AbortExampleTest {
    @TestToIgnore
    void test1() {
        var hasProblem = true;
        if (hasProblem) {
            abort();
        }
    }
}
