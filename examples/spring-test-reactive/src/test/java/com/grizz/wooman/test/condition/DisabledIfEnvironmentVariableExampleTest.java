package com.grizz.wooman.test.condition;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

@Slf4j
public class DisabledIfEnvironmentVariableExampleTest {
    @DisabledIfEnvironmentVariable(
            named = "TEST_BOOL",
            matches = "true"
    )
    @Test
    void testIfEnvironmentVariableIsNotTrue() {
        log.info("testIfEnvironmentVariableIsNotTrue");
    }

    @DisabledIfEnvironmentVariable(
            named = "TEST_VALUE",
            matches = "[a-z]+"
    )
    @Test
    void testIfEnvironmentVariableNotMatchesRegex() {
        log.info("testIfEnvironmentVariableNotMatchesRegex");
    }
}
