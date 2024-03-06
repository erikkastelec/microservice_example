package com.test.microservice_example.service;

import com.test.microservice_example.model.User;
import com.test.microservice_example.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;


    public User findUserByIdentifier(String identifier) {
        // Assuming a method in UserRepository that implements this logic
        return userRepository.findUserByIdentifier(identifier);
    }
    // User service methods
}
