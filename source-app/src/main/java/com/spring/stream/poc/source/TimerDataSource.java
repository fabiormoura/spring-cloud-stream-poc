package com.spring.stream.poc.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;

@EnableBinding(Source.class)
public class TimerDataSource {
    private static final Logger logger = LoggerFactory.getLogger(TimerDataSource.class);

    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedRate = "1000", taskExecutor = "taskExecutor"))
    public String produceData() {
        logger.info("Producing Hello Message");
        return "hello";
    }
}
