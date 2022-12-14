package com.artcources.artistica.model.binding;

import com.artcources.artistica.util.validations.UniqueUserEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ContactsBindingModel
{
    @NotBlank(message = "Name should not be an empty string")
    @Size(min=5,max=25,message = "Name must be between 5 and 25 characters.")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "User email should be valid")
    private String email;

    @NotBlank(message = "Subject cannot be empty")
    @Size(min=5,max=15,message = "Subject must be between 5 and 15 characters.")
    private String subject;

    @NotBlank(message = "Message body cannot be empty")
    @Size(min=5,max=500,message = "Message must be between 5 and 500 characters.")
    private String message;

    public String getName() {
        return name;
    }

    public ContactsBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactsBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public ContactsBindingModel setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ContactsBindingModel setMessage(String message) {
        this.message = message;
        return this;
    }

}
