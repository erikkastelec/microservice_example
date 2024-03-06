package com.test.microservice_example.service;

import com.test.microservice_example.dto.AddressCreationRequest;
import com.test.microservice_example.dto.AddressUpdateDTO;
import com.test.microservice_example.model.Address;
import com.test.microservice_example.model.User;
import com.test.microservice_example.repository.AddressRepository;
import com.test.microservice_example.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class AddressService {

    @Inject
    AddressRepository addressRepository;

    @Inject
    UserRepository userRepository;

    @Inject 
    UserService userService;

    public List<Address> listAllAddresses() {
        return addressRepository.listAll();
    }

    @Transactional
    public Address addAddress(AddressCreationRequest request) {
        if (request == null) {
            throw new BadRequestException("Invalid address data.");
        }

        Address address = mapAddressCreationRequestToAddress(request);

        // Call the overloaded addAddress method with the Address object
        return addAddress(address);
    }

    private Address mapAddressCreationRequestToAddress(AddressCreationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        User user = userService.findUserByIdentifier(request.getUserIdentifier());
        if (user == null) {
            throw new BadRequestException("User not found with the provided identifier.");
        }

        Address address = new Address();
        
        // Assuming User is already validated and fetched from the database
        address.setUser(user);
        address.setTitle(request.getTitle());
        address.setFirstName(request.getFirstName());
        address.setLastName(request.getLastName());
        address.setStreet(request.getStreet());
        address.setHouseNumber(request.getHouseNumber());
        address.setPostalCode(request.getPostalCode());
        address.setPostOfficeName(request.getPostOfficeName());
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        address.setIsDefault(request.getIsDefault() == null ? Boolean.FALSE : request.getIsDefault());

        return address;
    }

    @Transactional
    public Address addAddress(Address address) {
        if (address == null) {
            throw new BadRequestException("Invalid address data.");
        }

        User user = address.getUser();
        if (user == null) {
            throw new BadRequestException("User not found with the provided identifier.");
        }

        long addressCount = addressRepository.count("user =?1", user);
        if (addressCount >= 3) {
            throw new BadRequestException("User cannot have more than 3 addresses.");
        }

        // Validate the address fields using Bean Validation
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Address> violation : violations) {
                errorMessage.append(violation.getMessage()).append("; ");
            }
            throw new BadRequestException("Validation error: " + errorMessage.toString());
        }

        if (address.getCountry() == null || !"Slovenia".equals(address.getCountry())) {
            
            throw new BadRequestException("Only addresses in Slovenia are allowed. " + address.getCountry() + " is not.");
        }
        

        if (Boolean.TRUE.equals(address.getIsDefault()) || addressCount == 0) {
            
            Address currentDefault = addressRepository.find("isDefault = true and user = ?1", user).firstResult();
        
            if (currentDefault != null) {
                currentDefault.setIsDefault(false);
                addressRepository.persist(currentDefault);
            }
            address.setIsDefault(true);
        }

        addressRepository.persist(address);
        return address;
    }



    @Transactional
    public void updateAddress(Long id, AddressUpdateDTO dto) {
        Address address = addressRepository.findById(id);
        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            address.setTitle(dto.getTitle().orElse(null));
        }
        if (dto.getFirstName() != null && !dto.getFirstName().isEmpty()) {
            address.setFirstName(dto.getFirstName().orElse(null));
        }
        if (dto.getLastName() != null && !dto.getLastName().isEmpty()) {
            address.setLastName(dto.getLastName().orElse(null));
        }
        if (dto.getStreet() != null && !dto.getStreet().isEmpty()) {
            address.setStreet(dto.getStreet().orElse(null));
        }
        if (dto.getHouseNumber() != null && !dto.getHouseNumber().isEmpty()) {
            address.setHouseNumber(dto.getHouseNumber().orElse(null));
        }
        if (dto.getPostalCode() != null && !dto.getPostalCode().isEmpty()) {
            address.setPostalCode(dto.getPostalCode().orElse(null));
        }
        if (dto.getPostOfficeName() != null && !dto.getPostOfficeName().isEmpty()) {
            address.setPostOfficeName(dto.getPostOfficeName().orElse(null));
        }
        if (dto.getCity() != null && !dto.getCity().isEmpty()) {
            address.setCity(dto.getCity().orElse(null));
        }
        if (dto.getCountry() != null && !dto.getCountry().isEmpty()) {
            address.setCountry(dto.getCountry().orElse(null));
        }
        
    

        // Handle the isDefault field
        if (dto.getIsDefault() != null) {
            if (dto.getIsDefault().orElse(false) && !address.getIsDefault()) {
                // Unset current default if needed
                Address currentDefault = addressRepository.find("isDefault = true and user", address.getUser()).firstResult();
                if (currentDefault != null) {
                    currentDefault.setIsDefault(false);
                    addressRepository.persist(currentDefault);
                }
                address.setIsDefault(dto.getIsDefault().orElse(false));
            }
        }

        addressRepository.persist(address);
    }


        @Transactional
        public boolean deleteAddress(Long id) {
            Address address = addressRepository.findById(id);
            if (address != null) {
                // Logic for setting a new default address if the deleted address was the default
                if (address.getIsDefault()) {
                    // Get the first address of the user and set it as default
                    Address newDefault = addressRepository.find("user", address.getUser()).firstResult();
                    if (newDefault != null) {
                        newDefault.setIsDefault(true);
                        addressRepository.persist(newDefault);
                    }
                }
                addressRepository.delete(address);
                return true;
            }
            return false;
        }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    public List<Address> findAllAddressesByUserId(User user) {
        return addressRepository.find("user", user).list();
    }

    public Address findDefaultAddressByUserId(User user) {
        return addressRepository.find("isDefault = true and user =?1", user).firstResult();
    }
}
