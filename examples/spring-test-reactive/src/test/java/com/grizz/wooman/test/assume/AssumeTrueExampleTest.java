package com.grizz.wooman.test.assume;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class AssumeTrueExampleTest {
    @Test
    void test1() {
        assumeTrue(false);
    }
}
