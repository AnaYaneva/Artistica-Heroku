package com.artcources.artistica.model.service;


import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class MentorsAllServiceModel {
    private String id;
    private String username;
    private String email;
    private String photoUrl;
    private String firstName;

    private String lastName;


    private List<UserRoleEnum> userRoles = new ArrayList<>();

    private String facebook;
    private String linkedIn;
    private String instagram;

    public MentorsAllServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public MentorsAllServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public MentorsAllServiceModel setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public MentorsAllServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public MentorsAllServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public MentorsAllServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<UserRoleEnum> getUserRoles() {
        return userRoles;
    }

    public MentorsAllServiceModel setUserRoles(List<UserRoleEnum> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public String getId() {
        return id;
    }

    public MentorsAllServiceModel setId(String id) {
        this.id = id;
        return this;
    }



    public String getFacebook() {
        return facebook;
    }

    public MentorsAllServiceModel setFacebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public MentorsAllServiceModel setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    public String getInstagram() {
        return instagram;
    }

    public MentorsAllServiceModel setInstagram(String instagram) {
        this.instagram = instagram;
        return this;
    }
}
