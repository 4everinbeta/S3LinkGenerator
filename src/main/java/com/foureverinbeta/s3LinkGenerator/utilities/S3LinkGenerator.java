package com.foureverinbeta.s3LinkGenerator.utilities;

import ch.qos.logback.classic.Logger;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by Ryan on 9/28/16.
 */
public class S3LinkGenerator {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(S3LinkGenerator.class);

    public static String generateLink(String bucketName, String objectKey) {
        String awsAccessKeyId = System.getenv("AWS_ACCESS_KEY_ID");
        String awsSecret = System.getenv("AWS_SECRET_KEY");
        return generateLink(bucketName, objectKey, awsAccessKeyId, awsSecret);
    }

    public static String generateLink(String bucketName, String objectKey, String keyId, String keySecret) {

        //Remove +'s and/or encoded spaces if they exist.
        objectKey = objectKey.replace("+", " ")
                .replace("%2B", " ")
                .replace("%20", " ");

        ClientConfiguration clientConfiguration = new ClientConfiguration();

        // Required for new s3 v4 encryption policies
        clientConfiguration.setSignerOverride("AWSS3V4SignerType");

        AWSCredentials creds = new BasicAWSCredentials(keyId, keySecret);
        AmazonS3Client s3client = new AmazonS3Client(creds, clientConfiguration);

        // Hard-coded for now b/c we are limited to this region
        s3client.setEndpoint("s3-us-west-2.amazonaws.com");

        try {
            java.util.Date expiration = new java.util.Date();
            long milliSeconds = expiration.getTime();
            // TODO: Make this configuarble
            milliSeconds += 1000 * 60 * 60 * 24; // Add 1 week.
            expiration.setTime(milliSeconds);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey, HttpMethod.GET);
            generatePresignedUrlRequest.setExpiration(expiration);
            URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);

            return url.toString();
        } catch (AmazonServiceException exception) {
            LOG.error("Caught an AmazonServiceException, " +
                    "which means your request made it " +
                    "to Amazon S3, but was rejected with an error response " +
                    "for some reason.", exception);
            LOG.error("Error Message: " + exception.getMessage());
            LOG.error("HTTP  Code: "    + exception.getStatusCode());
            LOG.error("AWS Error Code:" + exception.getErrorCode());
            LOG.error("Error Type:    " + exception.getErrorType());
            LOG.error("Request ID:    " + exception.getRequestId());
        } catch (AmazonClientException ace) {
            LOG.error("Caught an AmazonClientException, " +
                    "which means the client encountered " +
                    "an internal error while trying to communicate" +
                    " with S3, " +
                    "such as not being able to access the network.", ace);
            LOG.error("Error Message: " + ace.getMessage());
        }
        return "";
    }
}
