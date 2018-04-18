package com.spring.stream.poc.source.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class DataProducerScheduleTask {
    private static final Logger logger = LoggerFactory.getLogger(DataProducerScheduleTask.class);

    @Scheduled(fixedRate = 500)
    public void produceData() {
        logger.info("TODO: produceData");
    }
}
