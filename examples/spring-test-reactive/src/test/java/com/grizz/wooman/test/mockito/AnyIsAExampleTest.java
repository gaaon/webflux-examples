package com.grizz.wooman.test.mockito;

import com.grizz.wooman.test.app.service.GreetingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnyIsAExampleTest {
    @Test
    void test1() {
        GreetingService mocked = mock();

        when(mocked.greeting(any(String.class)))
                .thenReturn("hi world");

        assertEquals("hi world", mocked.greeting("world"));
    }

    @Test
    void test2() {
        GreetingService mocked = mock();

        when(mocked.greeting(isA(String.class)))
                .thenReturn("hi world");

        assertEquals("hi world", mocked.greeting("world"));
    }
}
