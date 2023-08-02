package com.grizz.wooman.test.condition;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;

@Slf4j
public class EnabledOnJreExampleTest {
    @EnabledOnJre(JRE.JAVA_11)
    @Test
    void testOnJava11() {
        log.info("testNotOnJava8");
    }

    @EnabledOnJre(value = {JRE.JAVA_11, JRE.JAVA_12})
    @Test
    void testOnJava11And12() {
        log.info("testNotOnMacAarch64");
    }

    @EnabledOnJre(JRE.JAVA_10)
    @Test
    void testOnJava10() {
        log.info("testNotOnJava10");
    }
}
