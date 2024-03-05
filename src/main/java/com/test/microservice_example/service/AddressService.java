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

    public List<Address> listAllAddresses() {
        return addressRepository.listAll();
    }

    @Transactional
    public Address addAddress(Address address) {
        User user = findUserByIdentifier(address.getUser().getId().toString()); // Assuming the user is set in the address
        if (user == null) {
            throw new IllegalStateException("User not found.");
        }

        validateAddress(address);
        long addressCount = addressRepository.count("user", user);
        if (addressCount >= 3) {
            throw new IllegalStateException("User cannot have more than 3 addresses.");
        }

        if (address.getIsDefault()) {
            Address currentDefault = addressRepository.find("isDefault = true and user", user).firstResult();
            if (currentDefault != null) {
                currentDefault.setIsDefault(false);
                addressRepository.persist(currentDefault);
            }
        }

        address.setUser(user); // Ensure the user is associated with the address
        addressRepository.persist(address);
        return address;
    }

    @Transactional
    public Address updateAddress(Long id, Address updatedAddress) {
        Address existingAddress = addressRepository.findById(id);
        if (existingAddress == null) {
            return null;
        }

        validateAddress(updatedAddress);
        existingAddress.setStreet(updatedAddress.getStreet()); // Update fields as necessary
        // More field updates here...

        if (updatedAddress.getIsDefault() && !existingAddress.getIsDefault()) {
            Address currentDefault = addressRepository.find("isDefault = true and user", existingAddress.getUser()).firstResult();
            if (currentDefault != null) {
                currentDefault.setIsDefault(false);
                addressRepository.persist(currentDefault);
            }
            existingAddress.setIsDefault(true);
        }

        addressRepository.persist(existingAddress);
        return existingAddress;
    }

    @Transactional
    public boolean deleteAddress(Long id) {
        Address address = addressRepository.findById(id);
        if (address != null) {
            addressRepository.delete(address);
            return true;
        }
        return false;
    }

    private void validateAddress(Address address) {
        if (!"Slovenia".equals(address.getCountry())) {
            throw new IllegalArgumentException("Only addresses within the Republic of Slovenia can be entered.");
        }
        // Additional validations can be added here
    }

    public User findUserByIdentifier(String identifier) {
        // Assuming a method in UserRepository that implements this logic
        return userRepository.findUserByIdentifier(identifier);
    }
}
