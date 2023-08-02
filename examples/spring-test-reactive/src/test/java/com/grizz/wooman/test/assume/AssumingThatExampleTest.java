package com.grizz.wooman.test.assume;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumingThat;

public class AssumingThatExampleTest {
    @Test
    void test1() {
        var env = System.getenv("ENV");
        if (env == null) {
            env = "local";
        }

        assumingThat(env.equals("alpha"), () -> {
            assertEquals(2, 1);
        });
    }
}
