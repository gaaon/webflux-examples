package com.grizz.wooman.test.mockito;

import com.grizz.wooman.test.app.service.GreetingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class StubbingAnswerExampleTest {
    @Test
    void test1() {
        GreetingService mocked = mock();

        when(mocked.greeting(anyString()))
                .thenAnswer(invocation -> {
                    String name = invocation.getArgument(0);
                    if (name.equals("grizz")) {
                        throw new ArithmeticException();
                    }
                    return "hoi " + name;
                });

        assertEquals("hoi world", mocked.greeting("world"));
        assertThrows(ArithmeticException.class, () -> {
            mocked.greeting("grizz");
        });
    }
}