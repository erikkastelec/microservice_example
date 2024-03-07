package com.test.microservice_example.resource;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;

import com.test.microservice_example.model.Address;
import com.test.microservice_example.model.User;
import com.test.microservice_example.repository.AddressRepository;
import com.test.microservice_example.repository.UserRepository;
import com.test.microservice_example.service.AddressService;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
public class AddressResourceTest {

    @Test
    @Order(0)
    public void testGetAddressesEndpoint() {
        given()
            .when().get("/addresses")
            .then()
            .statusCode(200)
            // size more than 0
            .body("size()", greaterThan(0));
    }



    @Test
    public void testAddAddressEndpoint() {
        // JSON payload for the new address, including user identifier
        String newAddressJson = """
            {
                "userIdentifier": "User-003",
                "title": "Vacation Home",
                "firstName": "Alice",
                "lastName": "Smith",
                "street": "456 Beach St",
                "houseNumber": "2",
                "postalCode": "67890",
                "postOfficeName": "Beach Post Office",
                "city": "BeachCity",
                "country": "Slovenia",
                "isDefault": false
            }
            """;

        // Perform HTTP request to add the new address
        given()
            .contentType(ContentType.JSON)
            .body(newAddressJson)
            .when().post("/addresses")
            .then()
            .statusCode(201); // Expecting status code 201 for successful creation

        // Check that the address was added
        given()
            .when().get("/addresses")
            .then()
            .statusCode(200) // Expecting status code 200 for successful retrieval
            .body("find { it.street == '456 Beach St' && it.houseNumber == '2' }", notNullValue()); // Check that an address with the specified street and house number exists

    }
            
    @Test
    public void testSetDefaultIfFirstAddress() {
        // JSON payload for the new address, including user identifier
        String newAddressJson = """
            {
                "userIdentifier": "User-003",
                "title": "Vacation Home",
                "firstName": "Alice",
                "lastName": "Smith",
                "street": "456 Beach St",
                "houseNumber": "2",
                "postalCode": "67890",
                "postOfficeName": "Beach Post Office",
                "city": "BeachCity",
                "country": "Slovenia",
                "isDefault": false
            }
            """;

        // Perform HTTP request to add the new address
        given()
            .contentType(ContentType.JSON)
            .body(newAddressJson)
            .when().post("/addresses")
            .then()
            .statusCode(201); // Expecting status code 201 for successful creation

        // Check that the address was added
        given()
            .when().get("/users/User-003/addresses")
            .then()
            .statusCode(200) // Expecting status code 200 for successful retrieval
            .body("find { it.isDefault == true }", notNullValue());

    }   

    @Test
    public void testUpdateAddressEndpoint() {
        // JSON payload for the updated address
        Map<String, Object> updatedAddress = Map.of(
            "title", "Work",
            "street", "789 Updated St"
            // Add other fields as necessary
        );

        // Perform HTTP request to update the address
        given()
            .contentType(ContentType.JSON)
            .body(updatedAddress)
            .when().put("/addresses/1")
            .then()
            .statusCode(200); // Assuming status code 200 is expected for successful update

        // Fetch the updated address and verify changes
        given()
            .when().get("/addresses/1")
            .then()
            .statusCode(200) // Assuming 200 is returned for a successful retrieval
            .body("street", equalTo("789 Updated St")); // Verify the street was updated
    }

    
    @Test
    public void testDeleteAddress() {
        // Ensure there's an address with ID 1 and a corresponding user in import.sql

        // Perform the delete request for the address with ID 1
        given()
            .when().delete("/addresses/3")
            .then()
            .statusCode(204); // Expecting successful deletion

        // Attempt to fetch the deleted address to confirm deletion
        given()
            .when().get("/addresses/3")
            .then()
            .statusCode(404); // Expecting not found after deletion
    }

    @Test
    public void testAddFourthAddressFails() {
        // Assuming a user with ID 1 already exists and has 3 addresses
        // Define the fourth address data
        String newAddressJson = """
            {
                "userIdentifier": "User-001",
                "title": "Vacation Home",
                "firstName": "Alice",
                "lastName": "Smith",
                "street": "456 Beach St",
                "houseNumber": "2",
                "postalCode": "67890",
                "postOfficeName": "Beach Post Office",
                "city": "BeachCity",
                "country": "Slovenia",
                "isDefault": false
            }
            """;


        given()
            .contentType(ContentType.JSON)
            .body(newAddressJson)
            .when().post("/addresses")
            .then()
            .statusCode(201); // Verify the expected status code for exceeding the address limit'

        given()
            .contentType(ContentType.JSON)
            .body(newAddressJson)
            .when().post("/addresses")
            .then()
            .statusCode(400); // Verify the expected status code for exceeding the address limit'
            //.body("message", equalTo("User cannot have more than 3 addresses")); // Verify the expected error message

        
    }

    @Test
    public void testCountryMustBeSlovenia(){
        // JSON payload for the new address, including user identifier
        String newAddressJson = """
            {
                "userIdentifier": "User-001",
                "title": "Vacation Home",
                "firstName": "Alice",
                "lastName": "Smith",
                "street": "456 Beach St",
                "houseNumber": "2",
                "postalCode": "67890",
                "postOfficeName": "Beach Post Office",
                "city": "BeachCity",
                "country": "USA",
                "isDefault": false
            }
            """;

        // Perform HTTP request to add the new address
        given()
            .contentType(ContentType.JSON)
            .body(newAddressJson)
            .when().post("/addresses")
            .then()
            .statusCode(400); // Expecting status code 400 for invalid country

        newAddressJson = """
            {
                "userIdentifier": "User-002",
                "title": "Vacation Home",
                "firstName": "Alice",
                "lastName": "Smith",
                "street": "456 Beach St",
                "houseNumber": "2",
                "postalCode": "67890",
                "postOfficeName": "Beach Post Office",
                "city": "BeachCity",
                "country": "Slovenia",
                "isDefault": false
            }
            """;

        // Perform HTTP request to add the new address
        given()
            .contentType(ContentType.JSON)
            .body(newAddressJson)
            .when().post("/addresses")
            .then()
            .statusCode(201); // Expecting status code 201 for successful creation

    }

    @Test
    public void testSetDefaultTitle() {
        // JSON payload for the new address, including user identifier
        String newAddressJson = """
            {
                "userIdentifier": "User-003",
                "title": "",
                "firstName": "Alice",
                "lastName": "Smith",
                "street": "456 Beach St",
                "houseNumber": "2",
                "postalCode": "67890",
                "postOfficeName": "Beach Post Office",
                "city": "BeachCity",
                "country": "Slovenia",
                "isDefault": false
            }
            """;

        // Count how many addresses the user has


        // Perform HTTP request to add the new address
        given()
            .contentType(ContentType.JSON)
            .body(newAddressJson)
            .when().post("/addresses")
            .then()
            
            .statusCode(201); // Expecting status code 201 for successful creation
        Integer count = given()
            .when().get("/users/User-003/addresses")
            .then()
            .statusCode(200) // Expecting status code 200 for successful retrieval
            .extract().path("size()");
        // Check that the address was added
        given()
            .when().get("/users/User-003/addresses")
            .then()
            .statusCode(200) // Expecting status code 200 for successful retrieval
            // Check that an address with the specified street and house number exists
            .body("find { it.title == 'Naslov " + count + "' }", notNullValue());
            
    }

    @Test
    public void testAddressValidationFails() {
        // Define an address with missing required fields
        Map<String, Object> invalidAddress = new HashMap<>();
        invalidAddress.put("userId", 1);
        // Do not populate required fields to trigger validation errors

        given()
            .contentType(ContentType.JSON)
            .body(invalidAddress)
            .when().post("/addresses")
            .then()
            .statusCode(400);
    }
}