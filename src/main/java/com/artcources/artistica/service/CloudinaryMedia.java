package com.artcources.artistica.service;

public class CloudinaryMedia {

    private String url;
    private String publicId;

    public CloudinaryMedia() {
    }

    public String getUrl() {
        return url;
    }

    public CloudinaryMedia setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public CloudinaryMedia setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }
}

