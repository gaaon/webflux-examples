package com.grizz.wooman.test.mockito;

import com.grizz.wooman.test.app.service.GreetingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StubbingThrowExampleTest {
    @Test
    void test1() {
        GreetingService mocked = mock();

        when(mocked.greeting("world"))
                .thenThrow(IllegalStateException.class);

        assertThrows(IllegalStateException.class, () -> {
            mocked.greeting("world");
        });
    }

    @Test
    void test2() {
        GreetingService mocked = mock();
        when(mocked.greeting("world"))
                .thenThrow(
                        new IllegalStateException(),
                        new IllegalArgumentException(),
                        new ArithmeticException()
                );

        assertThrows(IllegalStateException.class, () -> {
            mocked.greeting("world");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            mocked.greeting("world");
        });
        assertThrows(ArithmeticException.class, () -> {
            mocked.greeting("world");
        });
    }
}
