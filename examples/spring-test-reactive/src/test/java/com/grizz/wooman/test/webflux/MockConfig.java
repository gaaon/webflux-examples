package com.grizz.wooman.test.webflux;

import com.grizz.wooman.test.app.service.GreetingService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class MockConfig {
    @MockBean
    private GreetingService greetingService;
}
