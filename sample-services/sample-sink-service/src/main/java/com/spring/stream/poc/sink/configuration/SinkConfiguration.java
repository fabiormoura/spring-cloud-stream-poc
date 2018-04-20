package com.spring.stream.poc.sink.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.stream.poc.sink.DataSink;

@Configuration
public class SinkConfiguration {
    @Bean
    public DataSink timerDataSource() {
        return new DataSink();
    }
}
