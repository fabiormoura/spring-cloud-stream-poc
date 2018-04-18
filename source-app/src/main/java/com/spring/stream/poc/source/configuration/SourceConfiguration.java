package com.spring.stream.poc.source.configuration;

import com.spring.stream.poc.source.TimerDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableConfigurationProperties({KinesisBinderConfigurationProperties.class})
public class SourceConfiguration {

    @Bean
    public TimerDataSource timerDataSource() {
        return new TimerDataSource();
    }

//    @Bean
//    public AmazonKinesisAsync amazonKinesis() {
//        return AmazonKinesisAsyncClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("", "")))
//                .withClientConfiguration(
//                        new ClientConfiguration()
//                                .withMaxErrorRetry(0)
//                                .withConnectionTimeout(1000))
//                .withEndpointConfiguration(
//                        new AwsClientBuilder.EndpointConfiguration("http://localhost:4568", DEFAULT_REGION.getName()))
//                .build();
//    }
//
//    @Bean
//    public MetadataStore kinesisCheckpointStore(KinesisBinderConfigurationProperties configurationProperties) {
//        AmazonDynamoDBAsync dynamoDB = AmazonDynamoDBAsyncClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("", "")))
//                .withClientConfiguration(
//                        new ClientConfiguration()
//                                .withMaxErrorRetry(0)
//                                .withConnectionTimeout(1000))
//                .withEndpointConfiguration(
//                        new AwsClientBuilder.EndpointConfiguration("http://localhost:4569", DEFAULT_REGION.getName()))
//                .build();
//
//        KinesisBinderConfigurationProperties.Checkpoint checkpoint = configurationProperties.getCheckpoint();
//
//        DynamoDbMetaDataStore kinesisCheckpointStore = new DynamoDbMetaDataStore(dynamoDB, checkpoint.getTable());
//        kinesisCheckpointStore.setReadCapacity(checkpoint.getReadCapacity());
//        kinesisCheckpointStore.setWriteCapacity(checkpoint.getWriteCapacity());
//        kinesisCheckpointStore.setCreateTableDelay(checkpoint.getCreateDelay());
//        kinesisCheckpointStore.setCreateTableRetries(checkpoint.getCreateRetries());
//
//        return kinesisCheckpointStore;
//    }
}
