package com.artcources.artistica.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserProfileUpdateBindingModel {

    @NotBlank(message = "First name should not be an empty string")
    @Size(min=3,max=20, message = "First name length must be between 3 and 20 characters!")
    private String firstName;

    @NotBlank(message = "Last name should not be an empty string")
    @Size(min=3,max=20, message = "Last name length must be between 3 and 20 characters!")
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
