package com.artcources.artistica.model.service;

import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class UsersAllServiceModel {

    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private List<UserRoleEnum> userRoles = new ArrayList<>();

    public UsersAllServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public UsersAllServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UsersAllServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UsersAllServiceModel setPassword(String password) {
        this.password = password;
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

    public List<UserRoleEnum> getUserRoles() {
        return userRoles;
    }

    public UsersAllServiceModel setUserRoles(List<UserRoleEnum> userRoles) {
        this.userRoles = userRoles;
        return this;
    }
}
