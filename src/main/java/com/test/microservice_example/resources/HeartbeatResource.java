package com.test.microservice_example.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/")
public class HeartbeatResource {

    @GET
    @Path("/heartbeat")
    public Response heartbeat() {
        return Response.ok("OK").build();
    }
}