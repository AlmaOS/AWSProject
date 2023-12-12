package com.project.RestAppAWS.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {
    @Value("${credentials.aws.accesskey}")
    private String AccessKeyAWS;

    @Value("${credentials.aws.secretkey}")
    private String SecretKeyAWS;

    @Value("${credentials.aws.region}")
    private String Region;

    @Value("${credentials.aws.sessiontoken}")
    private String SessionTokenAWS;

    @Bean
    public AmazonS3 amazonS3(){
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials()))
                .build();
    }

    public AWSCredentials awsCredentials(){
        return new BasicSessionCredentials(AccessKeyAWS,SecretKeyAWS,SessionTokenAWS);
    }

}
