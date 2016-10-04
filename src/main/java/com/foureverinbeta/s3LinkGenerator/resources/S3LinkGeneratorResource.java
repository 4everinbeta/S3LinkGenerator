package com.foureverinbeta.s3LinkGenerator.resources;

import com.codahale.metrics.annotation.Timed;
import com.foureverinbeta.s3LinkGenerator.api.Link;
import com.foureverinbeta.s3LinkGenerator.utilities.S3LinkGenerator;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.HttpURLConnection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;


/**
 * Created by Ryan on 9/27/16.
 * S3LinkGeneratorResource
 * This is the resource file for generating s3 links.
 * The get method takes the provided path
 */
@Path("/{bucket}/{objectid : (.+)?}")
@Produces(MediaType.APPLICATION_JSON)
public class S3LinkGeneratorResource {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(S3LinkGenerator.class);
    @GET
    @Timed
    public Link getLink(@Context UriInfo ui, @PathParam("bucket") String bucket, @PathParam("objectid") String objectId,
                        @QueryParam(value = "key") final Optional<String> key, @QueryParam(value = "secret") final Optional<String> secret) {

        ui.getQueryParameters().forEach((qk, qv) -> {System.out.println(qk); System.out.println(qv);});
        final String s3Link;
        if (key.isPresent() && !secret.isPresent()) {
                throw new WebApplicationException(
                        Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                                .entity("The \"secret\" param is missing. The \"key\" and \"secret\" params must always be passed together.")
                                .build()
                );
            }
        if (!key.isPresent() && secret.isPresent()) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity("Either the \"key\" param is missing. The \"key\" and \"secret\" params must always be passed together.")
                            .build()
            );
        }
        if (key.isPresent() && secret.isPresent())
            s3Link = S3LinkGenerator.generateLink(bucket, objectId, key.get(), secret.get());
        else
            s3Link = S3LinkGenerator.generateLink(bucket, objectId);
        return new Link(bucket, objectId, s3Link);
    }
}
