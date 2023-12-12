package com.project.RestAppAWS.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonSNSConfig {
    @Value("${credentials.aws.accesskey}")
    private String AccessKeyAWS;

    @Value("${credentials.aws.secretkey}")
    private String SecretKeyAWS;

    @Value("${credentials.aws.region}")
    private String Region;

    @Value("${credentials.aws.sessiontoken}")
    private String SessionTokenAWS;

    @Bean
    public AmazonSNS amazonSNS(){
        return AmazonSNSClientBuilder
                .standard()
                .withRegion(Region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
                .build();
    }

    public AWSCredentials awsCredentials(){
        return new BasicSessionCredentials(AccessKeyAWS, SecretKeyAWS, SessionTokenAWS);
    }
}
