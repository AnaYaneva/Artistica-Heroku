package com.artcources.artistica.model.view;

import com.artcources.artistica.model.entity.UserRoleEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class MentorProfileViewModel {

    private String username;

    private String firstName;

    private String lastName;

    private String photo;

    private String facebook;

    private String linkedIn;

    private String instagram;

    public MentorProfileViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public MentorProfileViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public MentorProfileViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public MentorProfileViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public MentorProfileViewModel setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public String getFacebook() {
        return facebook;
    }

    public MentorProfileViewModel setFacebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public MentorProfileViewModel setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    public String getInstagram() {
        return instagram;
    }

    public MentorProfileViewModel setInstagram(String instagram) {
        this.instagram = instagram;
        return this;
    }
}
