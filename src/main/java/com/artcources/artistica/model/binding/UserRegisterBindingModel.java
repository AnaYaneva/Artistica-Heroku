package com.artcources.artistica.model.binding;



import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.util.validations.FieldMatch;
import com.artcources.artistica.util.validations.UniqueUserEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message="Passwords do not match"
)
public class UserRegisterBindingModel {

    @NotBlank(message = "Username should not be an empty string")
    @Size(min=3,max=20, message = "Username length must be between 3 and 20 characters!")
    private String firstName;

    @NotBlank(message = "Username should not be an empty string")
    @Size(min=3,max=20, message = "Username length must be between 3 and 20 characters!")
    private String lastName;


    @NotBlank(message = "Email cannot be empty")
    @Email(message = "User email should be valid")
    @UniqueUserEmail(message = "User email should be unique")
    private String email;

    @NotBlank(message = "Password should not be an empty string")
    @Size(min=3,max=20, message = "Password length must be between 3 and 20 characters!")
    private String password;

    @NotBlank()
    @Size(min=3,max=20)
    private String confirmPassword;

    @NotNull(message = "You must select a experience level")
    private ExperienceLevelEnum level;



    public UserRegisterBindingModel() {
    }


    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ExperienceLevelEnum getLevel() {
        return level;
    }

    public UserRegisterBindingModel setLevel(ExperienceLevelEnum level) {
        this.level = level;
        return this;
    }
}
