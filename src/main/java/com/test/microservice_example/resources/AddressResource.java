package com.test.microservice_example.resources;

import java.util.List;

import com.test.microservice_example.model.Address;
import com.test.microservice_example.service.AddressService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
public class AddressResource {

    @Inject
    AddressService addressService;

    @GET
    @Operation(summary = "Get all addresses", description = "Retrieves a list of all addresses.")
    @APIResponse(responseCode = "200", description = "Successful retrieval",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class)))
    public List<Address> getAllAddresses() {
        return addressService.listAllAddresses();
    }

    @POST
    @Operation(summary = "Add a new address", description = "Adds a new address to the system.")
    @APIResponse(responseCode = "201", description = "Address created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class)))
    public Response addAddress(@RequestBody(description = "Address to add", required = true,
                                  content = @Content(schema = @Schema(implementation = Address.class))) Address address) {
        addressService.addAddress(address);
        return Response.status(Response.Status.CREATED).entity(address).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an address", description = "Updates the details of an existing address.")
    @APIResponse(responseCode = "200", description = "Address updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class)))
    @APIResponse(responseCode = "404", description = "Address not found")
    public Response updateAddress(@Parameter(description = "ID of the address to update", required = true) @PathParam("id") Long id,
                                  @RequestBody(description = "Updated address", required = true,
                                  content = @Content(schema = @Schema(implementation = Address.class))) Address address) {
        Address updatedAddress = addressService.updateAddress(id, address);
        if (updatedAddress == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updatedAddress).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete an address", description = "Deletes an address from the system.")
    @APIResponse(responseCode = "204", description = "Address deleted")
    @APIResponse(responseCode = "404", description = "Address not found")
    public Response deleteAddress(@Parameter(description = "ID of the address to delete", required = true) @PathParam("id") Long id) {
        boolean isDeleted = addressService.deleteAddress(id);
        if (isDeleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}