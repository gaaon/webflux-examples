package com.grizz.wooman.test.mockito;

import com.grizz.wooman.test.app.service.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;

@Slf4j
public class CreateMockExampleTest {
    @Test
    void createMock() {
        GreetingService mocked = mock();

        assertInstanceOf(GreetingService.class, mocked);
    }

    @Test
    void createMock2() {
        var mocked = mock(GreetingService.class);

        assertInstanceOf(GreetingService.class, mocked);
    }
}
