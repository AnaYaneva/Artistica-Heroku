package com.artcources.artistica.model.service;

import com.artcources.artistica.util.validations.UniqueUserEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MentorProfileUpdateServiceModel {


    private String firstName;

    private String lastName;

    private String facebook;

    private String linkedIn;

    private String instagram;

    public MentorProfileUpdateServiceModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public MentorProfileUpdateServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public MentorProfileUpdateServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


    public String getFacebook() {
        return facebook;
    }

    public MentorProfileUpdateServiceModel setFacebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public MentorProfileUpdateServiceModel setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    public String getInstagram() {
        return instagram;
    }

    public MentorProfileUpdateServiceModel setInstagram(String instagram) {
        this.instagram = instagram;
        return this;
    }
}
