package com.artcources.artistica.service;

public class CloudinaryVideo {

    private String url;
    private String publicId;

    public CloudinaryVideo() {
    }

    public String getUrl() {
        return url;
    }

    public CloudinaryVideo setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public CloudinaryVideo setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }
}

