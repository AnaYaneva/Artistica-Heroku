package com.artcources.artistica.model.binding;

public class CommentCreationBindingModel {
    private String username;
    private Long workshopId;
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
