package com.artcources.artistica.model.binding;

import com.artcources.artistica.util.validations.FieldMatch;
import com.artcources.artistica.util.validations.UniqueUserEmail;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message="Passwords do not match"
)
public class MentorRegisterBindingModel {

        @NotBlank(message = "Username should not be an empty string")
        @Size(min=3,max=20, message = "Username length must be between 3 and 20 characters!")
        private String firstName;

        @NotBlank(message = "Username should not be an empty string")
        @Size(min=3,max=20, message = "Username length must be between 3 and 20 characters!")
        private String lastName;


        @NotBlank(message = "Email cannot be empty")
        @Email(message = "User email should be valid")
        @UniqueUserEmail(message = "User email should be unique")
        private String username;

        @NotBlank(message = "Password should not be an empty string")
        @Size(min=3,max=20, message = "Password length must be between 3 and 20 characters!")
        private String password;

        @NotBlank()
        @Size(min=3,max=20)
        private String confirmPassword;

//        @NotBlank(message = "Photo name should not be an empty string")
//        @Size(min=3,max=20, message = "Photo name length must be between 3 and 20 characters!")
//        private String photoFileName;

        @NotNull(message = "Please add photo file")
        private MultipartFile photo;

        private String facebook;

        private String linkedIn;

        private String instagram;

    public MentorRegisterBindingModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public MentorRegisterBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public MentorRegisterBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public MentorRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MentorRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public MentorRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

//    public String getPhotoFileName() {
//        return photoFileName;
//    }
//
//    public MentorRegisterBindingModel setPhotoFileName(String photoFileName) {
//        this.photoFileName = photoFileName;
//        return this;
//    }

    public String getFacebook() {
        return facebook;
    }

    public MentorRegisterBindingModel setFacebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public MentorRegisterBindingModel setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    public String getInstagram() {
        return instagram;
    }

    public MentorRegisterBindingModel setInstagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public MentorRegisterBindingModel setPhoto(MultipartFile photo) {
        this.photo = photo;
        return this;
    }
}
