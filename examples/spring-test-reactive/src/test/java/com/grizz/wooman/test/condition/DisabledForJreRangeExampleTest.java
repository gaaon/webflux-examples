package com.grizz.wooman.test.condition;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.JRE;

@Slf4j
public class DisabledForJreRangeExampleTest {
    @DisabledForJreRange(
            min = JRE.JAVA_8,
            max = JRE.JAVA_11
    )
    @Test
    void testNotOnJava8To11() {
        log.info("testNotOnJava8To11");
    }

    @DisabledForJreRange(min = JRE.JAVA_12)
    @Test
    void testNotOnJava12OrHigher() {
        log.info("testNotOnJava12OrHigher");
    }

    @DisabledForJreRange(max = JRE.JAVA_10)
    @Test
    void testNotOnJava10OrLower() {
        log.info("testNotOnJava10OrLower");
    }
}
