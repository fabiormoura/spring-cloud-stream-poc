package com.spring.stream.poc.sink.configuration;

import com.spring.stream.poc.sink.DataSink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SinkConfiguration {
    @Bean
    public DataSink timerDataSource() {
        return new DataSink();
    }
}
