package com.spring.stream.poc.source.configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.kinesis.AmazonKinesisAsync;
import com.amazonaws.services.kinesis.AmazonKinesisAsyncClientBuilder;
import com.spring.stream.poc.source.TimerDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.kinesis.properties.KinesisBinderConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.aws.metadata.DynamoDbMetaDataStore;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static com.amazonaws.SDKGlobalConfiguration.AWS_CBOR_DISABLE_SYSTEM_PROPERTY;
import static com.amazonaws.regions.Regions.DEFAULT_REGION;

@Configuration
@EnableConfigurationProperties({KinesisBinderConfigurationProperties.class})
public class SourceConfiguration {
    @Autowired
    public SourceConfiguration(@Value("${kinesis.isLocal}") boolean useLocalKinesis) {
        // https://github.com/mhart/kinesalite#cbor-protocol-issues-with-the-java-sdk
        if (useLocalKinesis) {
            System.setProperty(AWS_CBOR_DISABLE_SYSTEM_PROPERTY, "true");
        }

    }

    @Bean
    TaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean
    public TimerDataSource timerDataSource() {
        return new TimerDataSource();
    }

    @Bean
    @ConditionalOnProperty(prefix = "kinesis", name = "isLocal", havingValue = "true")
    public AmazonKinesisAsync amazonKinesis() {
        return AmazonKinesisAsyncClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("", "")))
                .withClientConfiguration(
                        new ClientConfiguration()
                                .withMaxErrorRetry(0)
                                .withConnectionTimeout(1000))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:4568", DEFAULT_REGION.getName()))
                .build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "kinesis", name = "isLocal", havingValue = "true")
    public MetadataStore kinesisCheckpointStore(KinesisBinderConfigurationProperties configurationProperties) {
        AmazonDynamoDBAsync dynamoDB = AmazonDynamoDBAsyncClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("", "")))
                .withClientConfiguration(
                        new ClientConfiguration()
                                .withMaxErrorRetry(0)
                                .withConnectionTimeout(1000))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:4569", DEFAULT_REGION.getName()))
                .build();

        KinesisBinderConfigurationProperties.Checkpoint checkpoint = configurationProperties.getCheckpoint();

        DynamoDbMetaDataStore kinesisCheckpointStore = new DynamoDbMetaDataStore(dynamoDB, checkpoint.getTable());
        kinesisCheckpointStore.setReadCapacity(checkpoint.getReadCapacity());
        kinesisCheckpointStore.setWriteCapacity(checkpoint.getWriteCapacity());
        kinesisCheckpointStore.setCreateTableDelay(checkpoint.getCreateDelay());
        kinesisCheckpointStore.setCreateTableRetries(checkpoint.getCreateRetries());

        return kinesisCheckpointStore;
    }
}
