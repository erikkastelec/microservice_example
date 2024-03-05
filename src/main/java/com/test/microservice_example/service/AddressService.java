package com.test.microservice_example.service;

import com.test.microservice_example.model.Address;
import com.test.microservice_example.repository.AddressRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class AddressService {
    @Inject
    AddressRepository addressRepository;

    // Address service methods
}
