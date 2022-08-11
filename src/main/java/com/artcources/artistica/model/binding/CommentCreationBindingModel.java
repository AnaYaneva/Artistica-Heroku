package com.artcources.artistica.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CommentCreationBindingModel {
    private String username;
    private Long workshopId;

    @NotBlank(message = "Message is required!")
    @Size(min=5,max=500,message = "Message must be between 5 anf 500 characters.")
    private String message;

    public CommentCreationBindingModel(String username, Long workshopId, String message) {
        this.username = username;
        this.workshopId = workshopId;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Long workshopId) {
        this.workshopId = workshopId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
