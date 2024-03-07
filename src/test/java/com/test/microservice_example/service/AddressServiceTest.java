package com.test.microservice_example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

import com.test.microservice_example.model.Address;
import com.test.microservice_example.model.User;
import com.test.microservice_example.repository.AddressRepository;
import com.test.microservice_example.repository.UserRepository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

import org.mockito.Mock;
import org.mockito.Mockito;



@QuarkusTest
public class AddressServiceTest {

    @Inject
    AddressService addressService;

    @InjectMock
    AddressRepository addressRepository;

    @InjectMock
    UserRepository userRepository;

    
    @Test
    public void testAddNewAddressSuccess() {
        // Create a user
        User user = new User();
        user.setId(1L);
        user.setId1("1");
    
        // Mock the behavior of UserRepository to return the created user
        Mockito.when(userRepository.findUserByIdentifier("1")).thenReturn(user);
    
        // Create a new address
        Address newAddress = new Address();
        newAddress.setUser(user);
        newAddress.setTitle("Home"); // Sample title
        newAddress.setInstitutionName("Sample Institution"); // Sample institution name
        newAddress.setFirstName("John");
        newAddress.setLastName("Doe");
        newAddress.setStreet("123 Main St");
        newAddress.setHouseNumber("1");
        newAddress.setPostalCode("12345");
        newAddress.setPostOfficeName("Main Post Office");
        newAddress.setCity("City");
        newAddress.setCountry("Slovenia");
        newAddress.setIsDefault(true);
    
        // Mock the behavior of AddressRepository to return a non-null value
        // Mock the behavior of AddressRepository to return a mock PanacheQuery<Address> object
        Mockito.when(addressRepository.find("isDefault = true and user", user)).thenReturn(Mockito.mock(PanacheQuery.class));

    
        // Mock the behavior of AddressRepository
        Mockito.when(addressRepository.countByUserId(1L)).thenReturn(2L); // Less than 3 addresses
        Mockito.doAnswer(i -> i.getArguments()[0]).when(addressRepository).persist(Mockito.any(Address.class));
    
        // Call the service method
        Address savedAddress = addressService.addAddress(newAddress);
    
        // Assert that the saved address is not null
        assertNotNull(savedAddress);
    
        // Assert that each field of the saved address matches the input address
        assertEquals(newAddress.getTitle(), savedAddress.getTitle());
        assertEquals(newAddress.getInstitutionName(), savedAddress.getInstitutionName());
        assertEquals(newAddress.getFirstName(), savedAddress.getFirstName());
        assertEquals(newAddress.getLastName(), savedAddress.getLastName());
        assertEquals(newAddress.getStreet(), savedAddress.getStreet());
        assertEquals(newAddress.getHouseNumber(), savedAddress.getHouseNumber());
        assertEquals(newAddress.getPostalCode(), savedAddress.getPostalCode());
        assertEquals(newAddress.getPostOfficeName(), savedAddress.getPostOfficeName());
        assertEquals(newAddress.getCity(), savedAddress.getCity());
        assertEquals(newAddress.getCountry(), savedAddress.getCountry());
        assertEquals(newAddress.getIsDefault(), savedAddress.getIsDefault());
    
        // Verify that the persist method of AddressRepository was called once with the input address
        Mockito.verify(addressRepository, Mockito.times(1)).persist(newAddress);
    }
    

    @Test
    public void testDeleteAddress() {
        Address addressToDelete = new Address();
        addressToDelete.setId(1L); // Set up test data as needed
    
        Mockito.when(addressRepository.findById(addressToDelete.getId())).thenReturn(addressToDelete);
    
        boolean isDeleted = addressService.deleteAddress(addressToDelete.getId());
        assertTrue(isDeleted);
        Mockito.verify(addressRepository, Mockito.times(1)).delete(addressToDelete);
    }

}


