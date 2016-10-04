package com.foureverinbeta.s3LinkGenerator.Configuration;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.DecimalMax;

/**
 * Created by Ryan on 9/27/16.
 */
public class S3LinkGeneratorConfiguration  extends Configuration {
    @DecimalMax(value="86400000")
    private Long expiration;

    @JsonProperty
    public Long getExpiration() {
        return expiration;
    }

    @JsonProperty
    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
}