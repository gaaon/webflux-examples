package com.grizz.wooman.test.privat;

import com.grizz.wooman.test.app.service.GreetingService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrivateTestReflectionTestUtilsExampleTest {
    @SneakyThrows
    @Test
    void test1() {
        GreetingService greetingService = new GreetingService();

        String greeting = ReflectionTestUtils.invokeMethod(
                greetingService,
                "prepareGreeting",
                "world");

        assertEquals("hello world", greeting);

        Integer count = (Integer)ReflectionTestUtils.getField(
                greetingService, "count");
        assertEquals(100, count);

        ReflectionTestUtils.setField(greetingService,
                "count", 1000);

        count = (Integer)ReflectionTestUtils.getField(
                greetingService, "count");
        assertEquals(1000, count);

        ReflectionTestUtils.invokeSetterMethod(greetingService,
                "count", 10000, Integer.class);

        count = (Integer)ReflectionTestUtils.getField(
                greetingService, "count");
        assertEquals(10000, count);
    }
}
