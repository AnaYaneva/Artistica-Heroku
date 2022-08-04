package com.artcources.artistica.model.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseMediaEntity extends BaseEntity {

    private String name;
    private String url;
    private String publicId;


    public String getName() {
        return name;
    }

    public BaseMediaEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public BaseMediaEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public BaseMediaEntity setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }
}
