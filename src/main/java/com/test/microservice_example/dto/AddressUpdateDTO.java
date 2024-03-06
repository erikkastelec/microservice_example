package com.test.microservice_example.dto;

import java.util.Optional;

public class AddressUpdateDTO {

    private Optional<String> title = Optional.empty();
    private Optional<String> firstName = Optional.empty();
    private Optional<String> lastName = Optional.empty();
    private Optional<String> street = Optional.empty();
    private Optional<String> houseNumber = Optional.empty();
    private Optional<String> postalCode = Optional.empty();
    private Optional<String> postOfficeName = Optional.empty();
    private Optional<String> city = Optional.empty();
    private Optional<String> country = Optional.empty();
    private Optional<Boolean> isDefault = Optional.empty();
    
    public Optional<String> getTitle() {
        return title;
    }
    public void setTitle(Optional<String> title) {
        this.title = title;
    }
    public Optional<String> getFirstName() {
        return firstName;
    }
    public void setFirstName(Optional<String> firstName) {
        this.firstName = firstName;
    }
    public Optional<String> getLastName() {
        return lastName;
    }
    public void setLastName(Optional<String> lastName) {
        this.lastName = lastName;
    }
    public Optional<String> getStreet() {
        return street;
    }
    public void setStreet(Optional<String> street) {
        this.street = street;
    }
    public Optional<String> getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(Optional<String> houseNumber) {
        this.houseNumber = houseNumber;
    }
    public Optional<String> getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(Optional<String> postalCode) {
        this.postalCode = postalCode;
    }
    public Optional<String> getPostOfficeName() {
        return postOfficeName;
    }
    public void setPostOfficeName(Optional<String> postOfficeName) {
        this.postOfficeName = postOfficeName;
    }
    public Optional<String> getCity() {
        return city;
    }
    public void setCity(Optional<String> city) {
        this.city = city;
    }
    public Optional<String> getCountry() {
        return country;
    }
    public void setCountry(Optional<String> country) {
        this.country = country;
    }
    public Optional<Boolean> getIsDefault() {
        return isDefault;
    }
    public void setIsDefault(Optional<Boolean> isDefault) {
        this.isDefault = isDefault;
    }

    
}

