package com.artcources.artistica.model.service;

public class UserProfileUpdateServiceModel {

    private String firstName;

    private String lastName;

    public UserProfileUpdateServiceModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserProfileUpdateServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserProfileUpdateServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
