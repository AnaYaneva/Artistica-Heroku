package com.artcources.artistica.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity   extends BaseEntity {
    @Column(nullable = false,
            unique = true)
    private String username;

    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @OneToOne
    private MediaEntity photo;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEntity> userRoles = new ArrayList<>();

    @ManyToMany(mappedBy = "students")
    private List<OnlineWorkshopEntity> attending = new ArrayList<>();

    @Column(nullable = true)
    private String facebook;
    @Column(nullable = true)
    private String linkedIn;
    @Column(nullable = true)
    private String instagram;

    public UserEntity() {
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public MediaEntity getPhoto() {
        return photo;
    }

    public UserEntity setPhoto(MediaEntity photo) {
        this.photo = photo;
        return this;
    }

    public List<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public UserEntity setUserRoles(List<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public String getFacebook() {
        return facebook;
    }

    public UserEntity setFacebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public UserEntity setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    public String getInstagram() {
        return instagram;
    }

    public UserEntity setInstagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public List<OnlineWorkshopEntity> getAttending() {
        return attending;
    }

    public UserEntity setAttending(List<OnlineWorkshopEntity> students) {
        this.attending = students;
        return this;
    }

    @Override
    public String toString() {
        return
               firstName + ' ' +  lastName;
    }
}
