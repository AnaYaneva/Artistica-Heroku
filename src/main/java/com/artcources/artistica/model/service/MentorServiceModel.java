package com.artcources.artistica.model.service;

import com.artcources.artistica.model.entity.UserRoleEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class MentorServiceModel {
    @Column(nullable = false,
            unique = true)
    private String username;

    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEntity> userRoles = new ArrayList<>();
    @Column(nullable = true)
    private String facebook;
    @Column(nullable = true)
    private String linkedIn;
    @Column(nullable = true)
    private String instagram;

    public MentorServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public MentorServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MentorServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public MentorServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public MentorServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public MentorServiceModel setUserRoles(List<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public String getFacebook() {
        return facebook;
    }

    public MentorServiceModel setFacebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public MentorServiceModel setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    public String getInstagram() {
        return instagram;
    }

    public MentorServiceModel setInstagram(String instagram) {
        this.instagram = instagram;
        return this;
    }
}
