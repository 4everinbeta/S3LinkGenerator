package com.foureverinbeta.s3LinkGenerator.health;

import com.codahale.metrics.health.HealthCheck;
import com.foureverinbeta.s3LinkGenerator.utilities.S3LinkGenerator;

/**
 * Created by Ryan on 9/29/16.
 */
public class S3LinkGeneratorHealthCheck extends HealthCheck {

    public S3LinkGeneratorHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
