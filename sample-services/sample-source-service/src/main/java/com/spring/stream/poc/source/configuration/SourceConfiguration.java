package com.spring.stream.poc.source.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.spring.stream.poc.source.TimerDataSource;

@Configuration
public class SourceConfiguration {
    @Bean
    TaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean
    public TimerDataSource timerDataSource() {
        return new TimerDataSource();
    }
}
