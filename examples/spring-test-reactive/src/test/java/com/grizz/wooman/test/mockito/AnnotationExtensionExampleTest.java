package com.grizz.wooman.test.mockito;

import com.grizz.wooman.test.app.service.GreetingService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.internal.util.MockUtil;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AnnotationExtensionExampleTest {
    @RequiredArgsConstructor
    static class GreetingController {
        private final GreetingService greetingService;
    }

    @Spy
    @InjectMocks
    private GreetingController greetingController;

    @Mock
    private GreetingService greetingService;

    @Captor
    private ArgumentCaptor<String> captor;

    @Test
    void captureArgument() {
        greetingService.greeting("world");

        verify(greetingService).greeting(captor.capture());
        assertEquals("world", captor.getValue());
    }

    @Test
    void test1() {
        assertNotNull(greetingController.greetingService);

        assertTrue(MockUtil.isSpy(greetingController));
        assertTrue(MockUtil.isMock(
                greetingController.greetingService));
    }
}
