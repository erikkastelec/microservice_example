package com.test.microservice_example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String id1;
    private String id2;
    private String id3;

    // Add getters and setters for all fields
    public Long getId() {
        return id;
    }
    
}
