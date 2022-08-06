package com.artcources.artistica.model.entity;

import javax.persistence.Entity;

import javax.persistence.Table;

@Entity
@Table(name = "media")
public class MediaEntity extends BaseEntity {

    private String name;
    private String url;
    private String publicId;

    public MediaEntity() {
    }

    public String getName() {
        return name;
    }

    public MediaEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public MediaEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public MediaEntity setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

}
