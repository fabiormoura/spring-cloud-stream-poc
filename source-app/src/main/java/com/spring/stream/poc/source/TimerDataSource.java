package com.spring.stream.poc.source.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;

@EnableBinding(Source.class)
public class TimerDataSource {
    private static final Logger logger = LoggerFactory.getLogger(TimerDataSource.class);

//    @Scheduled(fixedRate = 500)
    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedRate = "500"))
    public String produceData() {
        logger.info("TODO: produceData");
        return "hello";
    }
}
