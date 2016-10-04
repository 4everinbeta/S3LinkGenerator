package com.foureverinbeta.s3LinkGenerator;

import com.foureverinbeta.s3LinkGenerator.Configuration.S3LinkGeneratorConfiguration;
import com.foureverinbeta.s3LinkGenerator.health.S3LinkGeneratorHealthCheck;
import com.foureverinbeta.s3LinkGenerator.resources.S3LinkGeneratorResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by Ryan on 9/27/16.
 */
public class s3LinkGeneratorApplication extends Application<S3LinkGeneratorConfiguration> {
    public static void main(String[] args) throws Exception {
        new s3LinkGeneratorApplication().run(args);
    }

    @Override
    public String getName() {
        return "s3LinkGenerator";
    }

    @Override
    public void initialize(Bootstrap<S3LinkGeneratorConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(S3LinkGeneratorConfiguration configuration,
                    Environment environment) {
        final S3LinkGeneratorResource resource = new S3LinkGeneratorResource();
        final S3LinkGeneratorHealthCheck healthCheck =
                new S3LinkGeneratorHealthCheck();
        environment.healthChecks().register("route", healthCheck);
        environment.jersey().register(resource);
    }

}