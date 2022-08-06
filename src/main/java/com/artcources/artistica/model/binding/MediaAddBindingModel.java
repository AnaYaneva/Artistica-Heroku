package com.artcources.artistica.model.binding;

import org.springframework.web.multipart.MultipartFile;

public class MediaAddBindingModel {

    private String name;
    private MultipartFile file;

    public MediaAddBindingModel() {
    }

    public String getName() {
        return name;
    }

    public MediaAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public MultipartFile getFile() {
        return file;
    }

    public MediaAddBindingModel setFile(MultipartFile file) {
        this.file = file;
        return this;
    }
}
