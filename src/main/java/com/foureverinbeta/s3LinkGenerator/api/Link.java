package com.foureverinbeta.s3LinkGenerator.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ryan on 9/27/16.
 */
public class Link {
    private String bucket;
    private String objectId;
    private String link;

    public Link() {
    }

    public Link(String bucket, String objectId, String s3Link) {
        this.bucket = bucket;
        this.objectId = objectId;
        this.link = s3Link;
    }

    @JsonProperty
    public String getBucket() {
        return bucket;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getLink() {
        return link;
    }
}
