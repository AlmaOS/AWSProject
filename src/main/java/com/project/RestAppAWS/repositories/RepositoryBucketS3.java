package com.project.RestAppAWS.repositories;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.nio.file.Paths;

@Repository
public class RepositoryBucketS3 {
    @Autowired
    private AmazonS3 amazonS3;

    public void uploadFileS3(String bucketname, String filename, File file){
        String key_name = Paths.get(file.getAbsolutePath()).getFileName().toString();
        try {
            amazonS3.putObject(new PutObjectRequest(bucketname, key_name, file));
        } catch (AmazonServiceException e) {
            System.out.println(e.getErrorCode() + e.getErrorMessage());
            throw e;
        }
    }
}
