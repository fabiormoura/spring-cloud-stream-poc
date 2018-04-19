package com.spring.stream.poc.sink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class DataSink {
    private static final Logger logger = LoggerFactory.getLogger(DataSink.class);

    @StreamListener(value = Sink.INPUT)
    public void readData(String message) {
        logger.info("Reading Message: {}", message);
    }
}
