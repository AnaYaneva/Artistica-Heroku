package com.artcources.artistica.model.entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public class BaseUserEntity  extends BaseEntity {
    @Column(nullable = false,
            unique = true)
    private String email;

    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEntity> userRoles = new ArrayList<>();

    public BaseUserEntity() {
    }

    public String getEmail() {
        return email;
    }

    public BaseUserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public BaseUserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public BaseUserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public BaseUserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public BaseUserEntity setUserRoles(List<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userRoles=" + userRoles +
                '}';
    }

}
