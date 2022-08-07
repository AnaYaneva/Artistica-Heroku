package com.artcources.artistica.model.service;

import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;

import java.util.ArrayList;
import java.util.List;

public class UsersAllServiceModel {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private List<UserRoleEntity> userRoles = new ArrayList<>();

    public UsersAllServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public UsersAllServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UsersAllServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }


    public String getFirstName() {
        return firstName;
    }

    public UsersAllServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UsersAllServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public UsersAllServiceModel setUserRoles(List<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }
}
