package org.sample.fault;

import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

@Path("/tolerance")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ToleranceResource {

    private static final Logger LOGGER = Logger.getLogger(ToleranceResource.class.getName());
    
    @GET
    @Path("/fault/fallback")
    @Fallback(fallbackMethod = "getFallbackRecommendations")
    public Response testFaultToleranceFallback(){
        var random = new Random().nextInt();
        LOGGER.info("RANDOM IS " + random);
        if(random%2==0){
            throw new NullPointerException();
        }
        return Response.status(Status.OK).entity("{\"v\": \"Primary\"}").build();
    }

    private Response getFallbackRecommendations(){
        LOGGER.info("Failback");
        return Response.status(Status.OK).entity("{\"v\": \"Fallback\"}").build();
    }

    @GET
    @Path("/fault/retry")
    @Retry( maxRetries = 4, retryOn = NullPointerException.class)
    public Response testFaultToleranceRetry(){
        var random = new Random().nextInt();
        LOGGER.info("RANDOM IS " + random);
        if(random%2==0){
            throw new NullPointerException();
        }
        return Response.status(Status.OK).entity("{\"v\": \"Retry\"}").build();
    }

    @GET
    @Path("/fault/timeout")
    @Timeout(value =1, unit =  ChronoUnit.SECONDS)
    public Response testFaultToleranceTimeout() throws InterruptedException{
        var random = new Random().nextInt();
        LOGGER.info("RANDOM IS " + random);
        if(random%2==0){
            LOGGER.info("sleep");
            Thread.sleep(2000);
            return Response.status(Status.OK).entity("{\"v\": \"Timeout\"}").build();
        }
        return Response.status(Status.OK).entity("{\"v\": \"Primary\"}").build();
    }


}
