package com.grizz.wooman.test.condition;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@Slf4j
public class EnabledOnOsExampleTest {
    @EnabledOnOs(OS.WINDOWS)
    @Test
    void testOnWindows() {
        log.info("testOnWindows");
    }

    @EnabledOnOs(OS.MAC)
    @Test
    void testOnMac() {
        log.info("testOnMac");
    }

    @EnabledOnOs(value = {OS.MAC},
            architectures = {"x86_64"})
    @Test
    void testOnMacX86() {
        log.info("testOnMacX86");
    }

    @EnabledOnOs(value = {OS.MAC},
            architectures = {"aarch64"})
    @Test
    void testOnMacAarch64() {
        log.info("testOnMacAarch64");
    }
}
