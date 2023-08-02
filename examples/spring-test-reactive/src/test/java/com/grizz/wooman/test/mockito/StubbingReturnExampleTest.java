package com.grizz.wooman.test.mockito;

import com.grizz.wooman.test.app.service.GreetingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StubbingReturnExampleTest {
    @Test
    void test1() {
        GreetingService mocked = mock();

        when(mocked.greeting("world"))
                .thenReturn("hi world");

        assertEquals("hi world", mocked.greeting("world"));
        assertEquals("hi world", mocked.greeting("world"));
    }

    @Test
    void test2() {
        GreetingService mocked = mock();

        when(mocked.greeting("world"))
                .thenReturn(
                        "hello world",
                        "hoi world",
                        "hi world"
                );

        assertEquals("hello world", mocked.greeting("world"));
        assertEquals("hoi world", mocked.greeting("world"));
        assertEquals("hi world", mocked.greeting("world"));
        assertEquals("hi world", mocked.greeting("world"));
    }

    @Test
    void test3() {
        GreetingService mocked = mock();

        when(mocked.greeting("world"))
                .thenReturn("hoi world")
                .thenReturn("hi world");

        assertEquals("hoi world", mocked.greeting("world"));
        assertEquals("hi world", mocked.greeting("world"));
    }
}
