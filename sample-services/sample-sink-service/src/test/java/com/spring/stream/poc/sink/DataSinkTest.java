package com.spring.stream.poc.sink;


import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.Logger.ROOT_LOGGER_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DataSinkTest {
    @Autowired
    private Sink dataSink;

    @Test
    public void shouldLogReceivedMessage() {
        Message<String> message = new GenericMessage<>("test-message");
        dataSink.input().send(message);

        Logger root = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);
        ListAppender<LoggingEvent> listAppender = (ListAppender) root.getAppender("LIST");
        assertThat(listAppender.list).extracting(LoggingEvent::getFormattedMessage).contains("Reading Message: test-message");
    }
}