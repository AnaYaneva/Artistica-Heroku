package com.artcources.artistica.model.view;

public class MediaViewModel {
    private Long id;
    private String name;
    private String url;
    private String publicId;

    public MediaViewModel() {
    }

    public Long getId() {
        return id;
    }

    public MediaViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MediaViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public MediaViewModel setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public MediaViewModel setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

}
