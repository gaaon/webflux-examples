package com.grizz.wooman.test.assertion;

import org.junit.jupiter.api.Test;

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssertTrueFalseExampleTest {
    @Test
    void checkTrue() {
        assertTrue(true);

    }

    @Test
    void checkTrueWithSupplier() {
        assertTrue(() -> {
            return true;
        });
    }

    @Test
    void checkFalse() {
        assertFalse(false);
    }
}
