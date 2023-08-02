package com.grizz.wooman.test.condition;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIf;

@Slf4j
public class DisabledIfEnabledIfExampleTest {
    boolean condition() {
        return true;
    }

    @DisabledIf(value = "condition")
    @Test
    void testIfConditionIsNotTrue() {
        log.info("testIfConditionIsNotTrue");
    }

    @EnabledIf(value = "condition")
    @Test
    void testIfConditionIsTrue() {
        log.info("testIfConditionIsTrue");
    }
}
