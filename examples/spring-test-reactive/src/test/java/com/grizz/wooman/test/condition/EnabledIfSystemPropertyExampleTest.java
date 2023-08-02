package com.grizz.wooman.test.condition;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

@Slf4j
public class EnabledIfSystemPropertyExampleTest {
    @EnabledIfSystemProperty(
            named = "test.bool",
            matches = "true"
    )
    @Test
    void testIfSystemPropertyIsTrue() {
        log.info("testIfSystemPropertyIsTrue");
    }

    @EnabledIfSystemProperty(
            named = "os.arch",
            matches = "[a-z1-9]+"
    )
    @Test
    void testIfSystemPropertyMatchesRegex() {
        log.info("testIfSystemPropertyMatchesRegex");
    }
}
