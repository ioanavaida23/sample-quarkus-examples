package org.sample.fault;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;

@Provider
public class TimeoutExceptionMapper implements ExceptionMapper<TimeoutException>{


    @Override
    public Response toResponse(TimeoutException ex){
        return Response.status(Status.OK).entity("{\"v\": \"TimeoutMapper\"}").build();
    }
}