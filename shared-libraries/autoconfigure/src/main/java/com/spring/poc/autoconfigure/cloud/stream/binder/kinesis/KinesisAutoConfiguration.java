package com.spring.poc.autoconfigure.cloud.stream.binder.kinesis;


import static com.amazonaws.SDKGlobalConfiguration.AWS_CBOR_DISABLE_SYSTEM_PROPERTY;
import static com.amazonaws.regions.Regions.DEFAULT_REGION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.kinesis.config.KinesisBinderConfiguration;
import org.springframework.cloud.stream.binder.kinesis.properties.KinesisBinderConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aws.metadata.DynamoDbMetaDataStore;
import org.springframework.integration.metadata.MetadataStore;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.kinesis.AmazonKinesisAsync;
import com.amazonaws.services.kinesis.AmazonKinesisAsyncClientBuilder;

@ConditionalOnClass(KinesisBinderConfiguration.class)
@AutoConfigureBefore({KinesisBinderConfiguration.class})
public class KinesisAutoConfiguration {
    @Configuration
    @ConditionalOnProperty(prefix = "kinesis.local", name = "enabled", havingValue = "true")
    @EnableConfigurationProperties({KinesisBinderConfigurationProperties.class, LocalKinesisConfigurationProperties.class})
    // https://github.com/mhart/kinesalite#cbor-protocol-issues-with-the-java-sdk
    protected class EnableLocalKinesisConfiguration {
        @Autowired
        public EnableLocalKinesisConfiguration() {
            System.setProperty(AWS_CBOR_DISABLE_SYSTEM_PROPERTY, "true");
        }

        @Bean
        public AmazonKinesisAsync amazonKinesis(LocalKinesisConfigurationProperties localKinesisConfigurationProperties) {
            return AmazonKinesisAsyncClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("", "")))
                    .withClientConfiguration(
                            new ClientConfiguration()
                                    .withMaxErrorRetry(0)
                                    .withConnectionTimeout(1000))
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(localKinesisConfigurationProperties.getKinesisServiceEndpoint(), DEFAULT_REGION.getName()))
                    .build();
        }

        @Bean
        public MetadataStore kinesisCheckpointStore(KinesisBinderConfigurationProperties configurationProperties, LocalKinesisConfigurationProperties localKinesisConfigurationProperties) {
            AmazonDynamoDBAsync dynamoDB = AmazonDynamoDBAsyncClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("", "")))
                    .withClientConfiguration(
                            new ClientConfiguration()
                                    .withMaxErrorRetry(0)
                                    .withConnectionTimeout(1000))
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(localKinesisConfigurationProperties.getDynamoDbServiceEndpoint(), DEFAULT_REGION.getName()))
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
}
