package com.artcources.artistica.model.view;

public class MentorsAllViewModel {
    private String id;

    private String username;
    private String firstName;

    private String lastName;

    private String facebook;
    private String linkedIn;
    private String instagram;

    private String photoUrl;

    public MentorsAllViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public MentorsAllViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getId() {
        return id;
    }

    public MentorsAllViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public MentorsAllViewModel setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public MentorsAllViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public MentorsAllViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFacebook() {
        return facebook;
    }

    public MentorsAllViewModel setFacebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public MentorsAllViewModel setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    public String getInstagram() {
        return instagram;
    }

    public MentorsAllViewModel setInstagram(String instagram) {
        this.instagram = instagram;
        return this;
    }
}
