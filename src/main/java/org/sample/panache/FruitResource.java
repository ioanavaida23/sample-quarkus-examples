package org.sample.panache;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

@Path("fruits")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class FruitResource {

    private static final Logger LOGGER = Logger.getLogger(FruitResource.class.getName());

    @GET
    public List<Fruit> get() {
        return Fruit.listAll();
    }

    @GET
    @Path("{id}")
    public Fruit getSingle(Long id) {
        return Fruit.findById(id);
    }

    @POST
    @Transactional
    public Response create(Fruit fruit) {
        if (fruit == null || fruit.id != null) {
            throw new WebApplicationException(422);
        }
        if (fruit.tree == null) {
            fruit.tree = new Tree(10L);
        }
        try {

            fruit.persist();
            return Response.ok(fruit).status(CREATED).build();
        } catch (RuntimeException e) {
            LOGGER.info("error " + e.getMessage());
        }
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(Long id) {
        var result = Fruit.deleteById(id);
        return result ? Response.ok().status(NO_CONTENT).build() : Response.ok().status(NOT_FOUND).build();
    }

    @GET
    @Path("/code/{code}")
    public List<Fruit> getFruitsWithATreeCode(@PathParam("code") String code) {
        List<Fruit> finalList = new ArrayList<>();

        List<Fruit> fruits = Fruit.listAll();
        fruits.stream().filter(t -> t.tree != null).filter(t -> t.tree.code.equals(Long.valueOf(code)))
                .forEach(finalList::add);
        return finalList;
    }

    @GET
    @Path("/count/{code}")
    public Long getFruitsWithATreeCodeCount(@PathParam("code") String code) {
        AtomicLong result = new AtomicLong();

        List<Fruit> fruits = Fruit.listAll();
        fruits.stream().filter(t -> t.tree != null).filter(t -> t.tree.code.equals(Long.valueOf(code)))
                .forEach(t -> result.getAndIncrement());

        return result.get();
    }
}
