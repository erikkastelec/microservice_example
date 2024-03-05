package com.test.microservice_example.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.test.microservice_example.model.Address;
import com.test.microservice_example.model.User;
import com.test.microservice_example.repository.AddressRepository;
import com.test.microservice_example.repository.UserRepository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
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
        User user = new User(); // Assuming User has an ID set for simplicity
        user.setId(1L);
        Address newAddress = new Address(); // Populate newAddress with appropriate test data
        newAddress.setUser(user);
        newAddress.setCountry("Slovenia"); // Ensure the country is set to "Slovenia" to pass validation
    
        Mockito.when(userRepository.findUserByIdentifier(user.getId().toString())).thenReturn(user);
        Mockito.when(addressRepository.countByUserId(user.getId())).thenReturn(2L); // Less than 3 addresses
        Mockito.doAnswer(i -> i.getArguments()[0]).when(addressRepository).persist(Mockito.any(Address.class));
    
        Address savedAddress = addressService.addAddress(newAddress);
        assertNotNull(savedAddress);
        Mockito.verify(addressRepository, Mockito.times(1)).persist(newAddress);
    }

    @Test
    public void testAddFourthAddressFails() {
        User user = new User(); // Mocked user
        user.setId(1L);
    
        Address fourthAddress = new Address(); // Set necessary fields
        fourthAddress.setUser(user);
        fourthAddress.setCountry("Slovenia"); // Ensure the country is set to "Slovenia" to pass validation
    
        Mockito.when(userRepository.findUserByIdentifier(user.getId().toString())).thenReturn(user);
        Mockito.when(addressRepository.count("user", user)).thenReturn(3L); // Simulate that the user already has 3 addresses
    
        assertThrows(IllegalStateException.class, () -> addressService.addAddress(fourthAddress));
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


