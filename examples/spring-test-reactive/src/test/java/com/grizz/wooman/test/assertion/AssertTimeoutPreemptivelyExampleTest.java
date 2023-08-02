package com.grizz.wooman.test.assertion;

import com.grizz.wooman.test.TestToFail;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class AssertTimeoutPreemptivelyExampleTest {
    @TestToFail
    void test1() {
        var duration = Duration.ofMillis(500);
        assertTimeoutPreemptively(duration, () -> {
            Thread.sleep(1000);
        });
    }
}
