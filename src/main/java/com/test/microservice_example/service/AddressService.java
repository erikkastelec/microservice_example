package com.test.microservice_example.service;

import com.test.microservice_example.model.Address;
import com.test.microservice_example.model.User;
import com.test.microservice_example.repository.AddressRepository;
import com.test.microservice_example.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AddressService {

    @Inject
    AddressRepository addressRepository;

    @Inject
    UserRepository userRepository;

    public List<Address> listAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Transactional
    public Address addOrUpdateAddress(Address address) {
        validateAddress(address);

        if (address.getId() == null) { // New address
            long addressCount = addressRepository.countByUserId(address.getUser().getId());
            if (addressCount >= 3) {
                throw new IllegalStateException("User cannot have more than 3 addresses.");
            }
        }

        if (address.getIsDefault()) {
            Address currentDefault = addressRepository.findDefaultByUserId(address.getUser().getId());
            if (currentDefault != null && !currentDefault.equals(address)) {
                currentDefault.setIsDefault(false);
                addressRepository.persist(currentDefault);
            }
        }

        addressRepository.persist(address);
        return address;
    }

    @Transactional
    public void deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId);
        if (address == null) {
            throw new IllegalStateException("Address not found.");
        }

        addressRepository.delete(address);
        // Check and set a new default if necessary
        if (address.getIsDefault()) {
            List<Address> remainingAddresses = addressRepository.findByUserId(address.getUser().getId());
            if (!remainingAddresses.isEmpty()) {
                remainingAddresses.get(0).setIsDefault(true); // Make the first of the remaining addresses the default
            }
        }
    }

    private void validateAddress(Address address) {
        if (address.getCountry() != null && !address.getCountry().equals("Slovenia")) {
            throw new IllegalArgumentException("Only addresses within the Republic of Slovenia can be entered.");
        }
        // Add more validation as needed, such as checking for required fields
    }

    // Method to fetch user by any of the unique identifiers (id1, id2, id3)
    public User findUserByIdentifier(String identifier) {
        return userRepository.findUserByIdentifier(identifier);
    }

}