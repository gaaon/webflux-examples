package com.grizz.wooman.test.mockweb;

import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class MockWebServerExampleTest {
    private MockWebServer mockWebServer;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void test() {
        log.info("port: {}",
                mockWebServer.getPort());
        log.info("hostName: {}",
                mockWebServer.getHostName());
        log.info("url: {}",
                mockWebServer.url("/api/v1/users"));
    }
}
