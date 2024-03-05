package com.test.microservice_example.repository;

import com.test.microservice_example.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    // Custom database operations if needed
}
