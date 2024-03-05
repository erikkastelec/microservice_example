package com.test.microservice_example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;
    private String institutionName;
    private String firstName;
    private String lastName;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String postOfficeName;
    private String city;
    private String country;
    private Boolean isDefault;

    // Constructors, getters, and setters
}