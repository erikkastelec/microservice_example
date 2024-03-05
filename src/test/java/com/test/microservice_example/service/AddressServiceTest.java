package com.test.microservice_example.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.test.microservice_example.model.Address;
import com.test.microservice_example.model.User;
import com.test.microservice_example.repository.AddressRepository;
import com.test.microservice_example.repository.UserRepository;

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
        User user = new User(); // Mocked user
        user.setId(1L);
        Address newAddress = new Address(); // Set necessary fields
        newAddress.setUser(user);

        Mockito.when(addressRepository.countByUserId(user.getId())).thenReturn(2L); // Less than 3 addresses
        Mockito.doAnswer(i -> i.getArguments()[0]).when(addressRepository).persist(Mockito.any(Address.class));
        Address savedAddress = addressService.addOrUpdateAddress(newAddress);
        assertNotNull(savedAddress);
        Mockito.verify(addressRepository, Mockito.times(1)).persist(newAddress);
    }

    @Test
    public void testAddFourthAddressFails() {
        User user = new User(); // Mocked user
        user.setId(1L);
        Address fourthAddress = new Address(); // Set necessary fields
        fourthAddress.setUser(user);

        Mockito.when(addressRepository.countByUserId(user.getId())).thenReturn(3L); // Already 3 addresses

        assertThrows(IllegalStateException.class, () -> addressService.addOrUpdateAddress(fourthAddress));
    }

    @Test
    public void testSetDefaultAddress() {
        User user = new User();
        user.setId(1L);

        Address defaultAddress = new Address(); // Existing default address
        defaultAddress.setUser(user);
        defaultAddress.setIsDefault(true);

        Address newDefaultAddress = new Address(); // New address to become default
        newDefaultAddress.setUser(user);
        newDefaultAddress.setIsDefault(true);

        Mockito.when(addressRepository.findDefaultByUserId(user.getId())).thenReturn(defaultAddress);
        Mockito.doAnswer(i -> i.getArguments()[0]).when(addressRepository).persist(Mockito.any(Address.class));

        addressService.addOrUpdateAddress(newDefaultAddress);

        assertFalse(defaultAddress.getIsDefault()); // The old default should no longer be default
        assertTrue(newDefaultAddress.getIsDefault()); // The new address should be set as default
    }




    }


