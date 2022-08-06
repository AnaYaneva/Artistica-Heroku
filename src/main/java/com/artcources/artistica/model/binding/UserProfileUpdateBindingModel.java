package com.artcources.artistica.model.binding;

public class UserProfileUpdateBindingModel {

    private String firstName;

    private String lastName;

    public UserProfileUpdateBindingModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserProfileUpdateBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserProfileUpdateBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
