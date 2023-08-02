package com.grizz.wooman.test.spring;

import com.grizz.wooman.test.app.controller.GreetingController;
import com.grizz.wooman.test.app.service.GreetingService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ExampleTestConfig {
    @Bean
    GreetingService greetingService() {
        return new GreetingService();
    }

    @Bean
    GreetingController greetingController(GreetingService greetingService) {
        return new GreetingController(greetingService);
    }
}
