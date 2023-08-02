package com.grizz.wooman.rms.stream.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogUtil {
    public void info(String format, Object arg) {
        log.info(format, arg);
    }
}
