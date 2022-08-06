package com.artcources.artistica.model.service;

import org.springframework.web.multipart.MultipartFile;

public class MediaAddServiceModel {
    private String name;
    private MultipartFile file;

    public MediaAddServiceModel() {
    }

    public String getName() {
        return name;
    }

    public MediaAddServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public MultipartFile getFile() {
        return file;
    }

    public MediaAddServiceModel setFile(MultipartFile file) {
        this.file = file;
        return this;
    }
}
