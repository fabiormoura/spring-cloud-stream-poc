package com.spring.stream.poc.source.configuration;

import com.spring.stream.poc.source.TimerDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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
