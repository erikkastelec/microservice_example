package com.test.microservice_example.resources;

import java.util.List;

import com.test.microservice_example.dto.AddressCreationRequest;
import com.test.microservice_example.dto.AddressUpdateDTO;
import com.test.microservice_example.model.Address;
import com.test.microservice_example.service.AddressService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
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

    // @Provider
    // public class JakartaExceptionMapper implements ExceptionMapper<Throwable> {

    //     @Override
    //     public Response toResponse(Throwable exception) {
    //         // Handle the exception here and return an appropriate response
    //         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    //     }
    // }
    // @Provider
    // class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    
    //     @Override
    //     public Response toResponse(IllegalArgumentException exception) {
    //         return Response.status(Response.Status.BAD_REQUEST)
    //                     .type(MediaType.APPLICATION_JSON_TYPE)
    //                     .entity(Map.of("error", exception.getMessage()))
    //                     .build();
    //     }
    // }
    
    
    
    @GET
    @Operation(summary = "Get all addresses", description = "Retrieves a list of all addresses.")
    @APIResponse(responseCode = "200", description = "Successful retrieval",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class)))
    public List<Address> getAllAddresses() {
        return addressService.listAllAddresses();
    }

    @GET
    @Operation(summary="Get address by ID", description = "Retrieves an address by its ID.")
    @APIResponse(responseCode = "200", description = "Address found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class)))
    @APIResponse(responseCode = "404", description = "Address not found")
    @Path("/{id}")
    public Response getAddressById(@Parameter(description = "ID of the address to retrieve", required = true) @PathParam("id") Long id) {
        Address address = addressService.getAddressById(id);
        if (address == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(address).build();
    }


    @POST
    @Operation(summary = "Add a new address", description = "Adds a new address to the system.")
    @APIResponse(responseCode = "201", description = "Address created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class)))
    public Response addAddress(@Valid @RequestBody(description = "Address to add", required = true,
                                content = @Content(schema = @Schema(implementation = AddressCreationRequest.class))) AddressCreationRequest request) {
        Address address = addressService.addAddress(request);
        return Response.status(Response.Status.CREATED).entity(address).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an address", description = "Updates an address in the system.")
    @APIResponse(responseCode = "200", description = "Address updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Address.class)))
    @APIResponse(responseCode = "404", description = "Address not found")
    public Response updateAddress(@PathParam("id") Long id, @Valid @RequestBody AddressUpdateDTO addressUpdateDTO) {
        
        addressService.updateAddress(id, addressUpdateDTO);
        Address updatedAddress = addressService.getAddressById(id);
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