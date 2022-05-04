package org.sample.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

@RequestScoped
@Path("/token")
public class TokenResource {

    @GET
    @Produces("text/plain")
    public Response getToken(@QueryParam("userType") String userType, @QueryParam("email") String email, @QueryParam("familyName") String familyName) {

        String token = Jwt.issuer("https://example.com/issuer")
                .upn(email)
                .groups(new HashSet<>(getGroupsBasedOnUserTyep(userType)))
                .claim(Claims.family_name.name(), familyName)
                .sign();
        System.out.println(token);
        return Response.ok().entity(token).build();
    }

    private List<String> getGroupsBasedOnUserTyep(String userType) {

        List<String> list = new ArrayList<>();
        if (userType.equals("normaluser")) {
            list.add("read");
        } else if (userType.equals("superuser")) {
            list.add("read");
            list.add("write");
        }
        return list;
    }
}
