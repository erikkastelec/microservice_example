package com.test.microservice_example.resources;

import java.util.List;

import com.test.microservice_example.model.Address;
import com.test.microservice_example.service.AddressService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
public class AddressResource {

    @Inject
    AddressService addressService;

    @GET
    public List<Address> getAllAddresses() {
        return addressService.listAllAddresses();
    }

    @POST
    public Response addAddress(Address address) {
        addressService.addAddress(address);
        return Response.status(Response.Status.CREATED).entity(address).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAddress(@PathParam("id") Long id, Address address) {
        Address updatedAddress = addressService.updateAddress(id, address);
        if (updatedAddress == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updatedAddress).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAddress(@PathParam("id") Long id) {
        boolean isDeleted = addressService.deleteAddress(id);
        if (isDeleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
