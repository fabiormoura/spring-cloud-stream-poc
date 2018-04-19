package com.spring.poc.autoconfigure.cloud.stream.binder.kinesis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kinesis.local")
public class LocalKinesisConfigurationProperties {
    private boolean enabled = false;
    private String kinesisServiceEndpoint = "http://localhost:4568";
    private String dynamoDbServiceEndpoint = "http://localhost:4569";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getKinesisServiceEndpoint() {
        return kinesisServiceEndpoint;
    }

    public void setKinesisServiceEndpoint(String kinesisServiceEndpoint) {
        this.kinesisServiceEndpoint = kinesisServiceEndpoint;
    }

    public String getDynamoDbServiceEndpoint() {
        return dynamoDbServiceEndpoint;
    }

    public void setDynamoDbServiceEndpoint(String dynamoDbServiceEndpoint) {
        this.dynamoDbServiceEndpoint = dynamoDbServiceEndpoint;
    }
}
