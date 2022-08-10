package com.artcources.artistica.model.service;

public class CommentMessageServiceModel {
    private String message;

    public CommentMessageServiceModel() {
    }

    public CommentMessageServiceModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
