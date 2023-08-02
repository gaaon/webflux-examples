package com.grizz.wooman.test.mockito;

import com.grizz.wooman.test.app.service.GreetingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StubbingVoidExampleTest {
    @Test
    void test1() {
        GreetingService mocked = mock();

        doThrow(ArithmeticException.class)
                .when(mocked)
                .hello("grizz");

        doNothing()
                .when(mocked)
                .hello("world");

        doReturn("hoi world")
                .when(mocked)
                .greeting("world");

        assertThrows(ArithmeticException.class, () -> {
            mocked.hello("grizz");
        });
        assertDoesNotThrow(() -> mocked.hello("world"));
        assertEquals("hoi world", mocked.greeting("world"));
    }
}
