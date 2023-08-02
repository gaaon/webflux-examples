package com.grizz.wooman.rms.stream.config;

import com.grizz.wooman.rms.stream.util.LogUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LogNameUnitTest {
    StreamFunctionConfig streamFunctionConfig =
            new StreamFunctionConfig();

    @Mock
    LogUtil log;

    @SneakyThrows
    @Test
    public void logName() {
        // given
        var nameFlux = Flux.just("grizz", "taewoo", "wooman");

        // when
        streamFunctionConfig.logName(log)
                .accept(nameFlux);

        Thread.sleep(100);

        // then
        verify(log).info("Name: {}", "grizz");
        verify(log).info("Name: {}", "taewoo");
        verify(log).info("Name: {}", "wooman");
    }
}
