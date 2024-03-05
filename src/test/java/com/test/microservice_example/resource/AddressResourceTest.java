package com.test.microservice_example.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

import com.test.microservice_example.model.Address;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AddressResourceTest {

    @Test
    public void testGetAddressesEndpoint() {
        given()
          .when().get("/addresses")
          .then()
             .statusCode(200)
             .body("$.size()", is(2)); // Expecting 2 addresses from import.sql
    }

    @Test
    public void testAddAddressEndpoint() {
        Address newAddress = new Address();
        // Populate newAddress with appropriate test data
        
        given()
          .contentType(ContentType.JSON)
          .body(newAddress)
          .when().post("/addresses")
          .then()
             .statusCode(200); // Adjust based on your API's expected behavior
    }

    @Test
    public void testUpdateAddress() {
        // Assuming there is an address with ID 1 in the database
        String updatedStreet = "789 Updated St";
        given()
            .contentType(ContentType.JSON)
            .body("{\"street\": \"" + updatedStreet + "\"}") // Minimal JSON body for update
            .when().put("/addresses/1")
            .then()
            .statusCode(200) // Assuming 200 is the success status for update
            .body("street", is(updatedStreet));
    }

    @Test
    public void testDeleteAddress() {
        // Assuming there is an address with ID 2 that can be deleted
        given()
            .when().delete("/addresses/2")
            .then()
            .statusCode(204); // Assuming 204 is the success status for delete

        // Verify the address is no longer available
        given()
            .when().get("/addresses/2")
            .then()
            .statusCode(404); // Assuming 404 is returned for a non-existent address
    }

    @Test
    public void testAddFourthAddressFails() {
        // Attempt to add a fourth address for a user with 3 addresses
        Address fourthAddress = new Address(); // Populate with test data as needed

        given()
            .contentType(ContentType.JSON)
            .body(fourthAddress)
            .when().post("/addresses")
            .then()
            .statusCode(400) // Assuming 400 or another appropriate status code for failure
            .body("error", is("User cannot have more than 3 addresses"));
    }

    @Test
    public void testAddressValidationFails() {
        Address invalidAddress = new Address(); // Construct an invalid address (e.g., missing required fields)

        given()
            .contentType(ContentType.JSON)
            .body(invalidAddress)
            .when().post("/addresses")
            .then()
            .statusCode(400) // Assuming 400 for validation errors
            .body("errors", hasItem(containsString("required field"))); // Check for specific validation error messages
    }
}
