package com.artcources.artistica.model.binding;

import com.artcources.artistica.util.validations.FieldMatch;
import com.artcources.artistica.util.validations.UniqueUserEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class MentorProfileUpdateBindingModel {
    @NotBlank(message = "Username should not be an empty string")
    @Size(min=3,max=20, message = "Username length must be between 3 and 20 characters!")
    private String firstName;

    @NotBlank(message = "Username should not be an empty string")
    @Size(min=3,max=20, message = "Username length must be between 3 and 20 characters!")
    private String lastName;


    private String facebook;

    private String linkedIn;

    private String instagram;

    public MentorProfileUpdateBindingModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public MentorProfileUpdateBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public MentorProfileUpdateBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFacebook() {
        return facebook;
    }

    public MentorProfileUpdateBindingModel setFacebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public MentorProfileUpdateBindingModel setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    public String getInstagram() {
        return instagram;
    }

    public MentorProfileUpdateBindingModel setInstagram(String instagram) {
        this.instagram = instagram;
        return this;
    }
}
