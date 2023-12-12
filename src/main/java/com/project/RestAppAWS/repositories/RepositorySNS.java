package com.project.RestAppAWS.repositories;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorySNS {
    @Autowired
    private AmazonSNS amazonSNS;

    public void sendEmail(String text, String topicName){
        PublishResult result = amazonSNS.publish(new PublishRequest()
                .withTopicArn("arn:aws:sns:us-east-1:245540180048:MensajeProyecto")
                .withMessage(text)
        );
        System.out.println(result);
    }
}
