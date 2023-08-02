package com.grizz.wooman.test.mockito;

import com.grizz.wooman.test.app.service.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Slf4j
public class ExecuteMockExampleTest {
    @Test
    void mockMethods() {
        GreetingService mocked = mock();

        // do nothing
        mocked.hello("world");

        var actualCount = mocked.greetingCount();
        assertEquals(0, actualCount);

        var actualGreeting = mocked.greeting("world");
        assertNull(actualGreeting);

        var actualMono = mocked.greetingMono("world");
        assertNull(actualMono);
    }
}
