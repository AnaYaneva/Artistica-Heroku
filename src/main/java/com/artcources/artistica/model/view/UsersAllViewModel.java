package com.artcources.artistica.model.view;

import com.artcources.artistica.model.entity.UserRoleEntity;
import com.artcources.artistica.model.enums.UserRoleEnum;

import java.util.List;

public class UsersAllViewModel {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;

    private boolean isAdmin;

    private boolean isMentor;

    private List<UserRoleEntity> userRoles;

    public UsersAllViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UsersAllViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UsersAllViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UsersAllViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UsersAllViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public UsersAllViewModel setUserRoles(List<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public UsersAllViewModel setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }

    public boolean isMentor() {
        return isMentor;
    }

    public UsersAllViewModel setMentor(boolean mentor) {
        isMentor = mentor;
        return this;
    }
}
