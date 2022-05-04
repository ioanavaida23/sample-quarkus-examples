package org.sample.panache;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@ApplicationScoped
@Path("/trees")
@Produces("application/json")
@Consumes("application/json")
public class TreeResource {

    private static final Logger LOGGER = Logger.getLogger(FruitResource.class.getName());

    @GET
    @Counted(name = "tree.by.id", description = "How many times was tree by id called", absolute = true

    )
    @Path("{id}")
    @Transactional
    public Tree getSingle(@PathParam("id") String id) {
        LOGGER.info("Getting tree by id");
        return Tree.findById(Long.valueOf(id));
    }

    @POST
    @Timed(name = "time.create.tree", absolute = true, description = "time it takes to create a new tree", unit = MetricUnits.MILLISECONDS)
    @Transactional
    public Response create(Tree tree) {
        if (tree == null || tree.id != null) {
            throw new WebApplicationException(422);
        }
        
        try {

            tree.persist();
            return Response.ok(tree).status(Status.CREATED).build();
        } catch (RuntimeException e) {
            LOGGER.info("error " + e.getMessage());
        }
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

}
