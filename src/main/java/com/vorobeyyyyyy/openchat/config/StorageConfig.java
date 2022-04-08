package com.vorobeyyyyyy.openchat.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

@Configuration
@AllArgsConstructor
@Slf4j
public class StorageConfig {

    private OpenChatProperties openChatProperties;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                openChatProperties.getStorage().getAccessKey(),
                openChatProperties.getStorage().getSecretKey()
        );
        log.info("Creating S3 client with credentials: {}", credentials);
        S3Client s3Client = S3Client.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(openChatProperties.getStorage().getEndpoint())
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        s3Client.createBucket(CreateBucketRequest.builder()
                .bucket(openChatProperties.getStorage().getBucketName())
                .build());

        return s3Client;
    }
}
