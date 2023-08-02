package com.grizz.wooman.rms.stream;

import com.grizz.wooman.rms.stream.util.LogUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@Import(TestChannelBinderConfiguration.class)
@ActiveProfiles("stream")
@AutoConfigureWebTestClient
@SpringBootTest(
        classes = StreamApplication.class
)
public class StreamTest {
    @Autowired
    InputDestination inputDestination;

    @Autowired
    OutputDestination outputDestination;

    @Autowired
    WebTestClient webTestClient;

    @SpyBean
    LogUtil mockLogUtil;

    @Test
    void contextLoad() {}

    @Test
    void addGreeting() {
        // given
        var payload = "world";
        var input = new GenericMessage<>(payload);
        var inputBinding = "addGreeting-in-0";
        var outputBinding = "addGreeting-out-0";

        // when
        inputDestination.send(input, inputBinding);

        // then
        var output = outputDestination.receive(0, outputBinding);
        String outputMessage = new String(output.getPayload());

        var expected = "Hello " + payload + "!";
        assertEquals(expected, outputMessage);
    }

    @Test
    void logName() {
        // given
        var payload = "grizz";
        var input = new GenericMessage<>(payload);
        var inputBinding = "logName-in-0";

        // when
        inputDestination.send(input, inputBinding);

        // then
        verify(mockLogUtil).info("Name: {}", payload);
    }

    @Test
    void supplyNames() {
        // given
        var outputBinding = "supplyNames-out-0";
        var expectedNames = List.of(
                "grizz", "taewoo", "wooman");

        // when
        for (var name : expectedNames) {
            var output = outputDestination.receive(0, outputBinding);
            String outputMessage = new String(
                    output.getPayload());

            assertEquals(name, outputMessage);
        }
    }

    @Test
    void streamBridge() {
        // given
        var name = "grizz";
        var outputBinding = "addGreeting-out-0";

        // when
        webTestClient
                .get()
                .uri("/greeting?name=" + name)
                .exchange()
                .expectStatus().isOk();

        // then
        var output = outputDestination.receive(
                0, outputBinding);
        String outputMessage = new String(
                output.getPayload());

        var expected = "Hello " + name + "!";
        assertEquals(expected, outputMessage);
    }
}
