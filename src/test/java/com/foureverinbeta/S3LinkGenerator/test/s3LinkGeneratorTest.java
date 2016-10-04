package com.foureverinbeta.S3LinkGenerator.test;

import com.foureverinbeta.s3LinkGenerator.utilities.S3LinkGenerator;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Ryan on 9/28/16.
 */
public class s3LinkGeneratorTest {

    @Test
    public void GenerateLinkTestWithCreds() {
        String bucket = "helix-enrollmentcenter-nlu-preprod";
        String objectKey = "nlu-enroll-retain-uat/01f83e07-9478-e611-8109-0050568a2714/Test+Upload+File2.txt";
//        String localUrl = "https://s3-us-west-2.amazonaws.com/helix-enrollmentcenter-nlu/nlu/36ba6ebf-757c-e611-80e6-00505603073b/9873c248-6742-451d-830f-63b4d7e4f932.pdf";
//        String localUrl = "https://s3-us-west-2.amazonaws.com/helix-enrollmentcenter-documentshare-nlu/helixTest/teststudent/mytest.txt";
        String tmpUrl = S3LinkGenerator.generateLink(bucket, objectKey, "AKIAJQOZ3ROVOZCXA2BA", "PN2n8uKZQAkHobc3vEM2826DnepxNTECtWpcvtNj");
//        tmpUrl = tmpUrl.replace("%2B", "+");
        Assert.assertNotNull(tmpUrl);
        Assert.assertNotEquals(tmpUrl, "");
        System.out.println(tmpUrl);
    }

    @Test
    public void GenerateLinkTestWithoutCreds() {
        String bucket = "helix-enrollmentcenter-nlu-preprod";
        String objectKey = "nlu-enroll-retain-uat/01f83e07-9478-e611-8109-0050568a2714/Test+Upload+File2.txt";
//        String localUrl = "https://s3-us-west-2.amazonaws.com/helix-enrollmentcenter-nlu/nlu/36ba6ebf-757c-e611-80e6-00505603073b/9873c248-6742-451d-830f-63b4d7e4f932.pdf";
//        String localUrl = "https://s3-us-west-2.amazonaws.com/helix-enrollmentcenter-documentshare-nlu/helixTest/teststudent/mytest.txt";
        String tmpUrl = S3LinkGenerator.generateLink(bucket, objectKey);
//        tmpUrl = tmpUrl.replace("%2B", "+");
        Assert.assertNotNull(tmpUrl);
        Assert.assertNotEquals(tmpUrl, "");
        System.out.println(tmpUrl);
    }
}
