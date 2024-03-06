package com.test.microservice_example.resources;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import com.test.microservice_example.model.Address;
import com.test.microservice_example.model.User;
import com.test.microservice_example.service.AddressService;
import com.test.microservice_example.service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService; // Assuming you have a UserService

    @Inject
    AddressService addressService; // Assuming you have an AddressService

    @GET
    @Path("/{userId}/addresses")
    @Operation(summary = "Get all addresses for a user", 
               description = "Retrieves all addresses associated with a given user.")
    @APIResponse(responseCode = "200", description = "Successful retrieval", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = Address.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "404", description = "User not found")
    public Response getUserAddresses(@Parameter(description = "ID of the user to retrieve addresses for", required = true) 
                                     @PathParam("userId") String userId) {

        User user = userService.findUserByIdentifier(userId); // Method to find user by ID
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Address> addresses = addressService.findAllAddressesByUserId(user); // Method to get addresses by user ID
        return Response.ok(addresses).build();
    }

    
}
