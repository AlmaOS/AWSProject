package com.project.RestAppAWS.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {
    @Value("${credentials.dynamodb.endpoint}")
    private String DynamoDBEndpoint;

    @Value("${credentials.aws.accesskey}")
    private String AccessKeyAWS;

    @Value("${credentials.aws.secretkey}")
    private String SecretKeyAWS;

    @Value("${credentials.aws.region}")
    private String Region;

    @Value("${credentials.aws.sessiontoken}")
    private String SessionTokenAWS;

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAmazonDynamoDB());
    }

    private AmazonDynamoDB buildAmazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                DynamoDBEndpoint, Region)
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicSessionCredentials(
                                        AccessKeyAWS,
                                        SecretKeyAWS,
                                        SessionTokenAWS  // Proporciona el token de sesión
                                )
                        )
                )
                .build();
    }

}
