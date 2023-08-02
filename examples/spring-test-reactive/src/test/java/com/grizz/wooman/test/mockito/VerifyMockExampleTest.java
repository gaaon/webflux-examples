package com.grizz.wooman.test.mockito;

import com.grizz.wooman.test.app.service.GreetingService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class VerifyMockExampleTest {
    @Test
    void verifyMock() {
        GreetingService mocked = mock();

        mocked.hello("world");

        verify(mocked).hello(argThat(s -> s.equals("world")));
    }
}
