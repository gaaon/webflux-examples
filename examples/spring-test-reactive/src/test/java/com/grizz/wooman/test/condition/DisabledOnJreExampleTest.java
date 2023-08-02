package com.grizz.wooman.test.condition;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

@Slf4j
public class DisabledOnJreExampleTest {
    @DisabledOnJre(JRE.JAVA_11)
    @Test
    void testNotOnJava11() {
        log.info("testNotOnJava8");
    }

    @DisabledOnJre(value = {JRE.JAVA_11, JRE.JAVA_12})
    @Test
    void testNotOnJava11And12() {
        log.info("testNotOnMacAarch64");
    }

    @DisabledOnJre(JRE.JAVA_10)
    @Test
    void testNotOnJava10() {
        log.info("testNotOnJava10");
    }
}
