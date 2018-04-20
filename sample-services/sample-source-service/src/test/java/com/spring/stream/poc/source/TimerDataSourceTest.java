package com.spring.stream.poc.source;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimerDataSourceTest {
    @Autowired
    Source source;

    @Autowired
    private MessageCollector messageCollector;

    @Test
    public void shouldSendHelloMessage() throws InterruptedException {
        Message<String> message = (Message<String>) messageCollector.forChannel(source.output()).poll(2000, TimeUnit.MILLISECONDS);
        assertThat(message.getPayload()).isEqualTo("hello");
    }
}