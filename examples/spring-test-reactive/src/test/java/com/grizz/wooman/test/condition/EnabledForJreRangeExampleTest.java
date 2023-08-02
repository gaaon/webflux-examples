package com.grizz.wooman.test.condition;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;

@Slf4j
public class EnabledForJreRangeExampleTest {
    @EnabledForJreRange(
            min = JRE.JAVA_8,
            max = JRE.JAVA_11
    )
    @Test
    void testOnJava8To11() {
        log.info("testOnJava8To11");
    }

    @EnabledForJreRange(min = JRE.JAVA_12)
    @Test
    void testOnJava12OrHigher() {
        log.info("testOnJava12OrHigher");
    }

    @EnabledForJreRange(max = JRE.JAVA_10)
    @Test
    void testOnJava10OrLower() {
        log.info("testOnJava10OrLower");
    }
}
