package com.test.microservice_example.repository;

import com.test.microservice_example.model.Address;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddressRepository implements PanacheRepository<Address> {
    // Custom database operations if needed
}