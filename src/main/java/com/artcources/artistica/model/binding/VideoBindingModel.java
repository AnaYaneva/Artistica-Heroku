package com.artcources.artistica.model.binding;

import org.springframework.web.multipart.MultipartFile;

public class VideoBindingModel {

    private String title;
    private MultipartFile video;

    public VideoBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public VideoBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public MultipartFile getVideo() {
        return video;
    }

    public VideoBindingModel setVideo(MultipartFile video) {
        this.video = video;
        return this;
    }
}
