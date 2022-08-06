package com.artcources.artistica.model.view;

import javax.persistence.Column;

public class UserProfileViewModel {
    private String username;

    private String firstName;

    private String lastName;

    public UserProfileViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserProfileViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserProfileViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserProfileViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
