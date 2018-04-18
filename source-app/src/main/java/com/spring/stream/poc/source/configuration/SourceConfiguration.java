package com.spring.stream.poc.source.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SourceConfiguration {

    @Bean
    public DataProducerScheduleTask generateDataScheduleTask() {
        return new DataProducerScheduleTask();
    }
}
