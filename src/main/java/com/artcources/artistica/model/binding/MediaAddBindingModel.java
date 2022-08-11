package com.artcources.artistica.model.binding;

import com.artcources.artistica.util.validations.MultipartFileNotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MediaAddBindingModel {

    @NotBlank(message = "Picture name is required!")
    @Size(min=1,max=20,message = "Picture name must be between 1 and 20 characters!")
    private String name;

    @MultipartFileNotEmpty(message = "Picture file is required!")
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
