package com.artcources.artistica.model.service;

import org.springframework.web.multipart.MultipartFile;

public class VideoAddServiceModel {
    private String nameVideo;
    private MultipartFile videoFile;

    public VideoAddServiceModel() {
    }

    public String getNameVideo() {
        return nameVideo;
    }

    public VideoAddServiceModel setNameVideo(String nameVideo) {
        this.nameVideo = nameVideo;
        return this;
    }

    public MultipartFile getVideoFile() {
        return videoFile;
    }

    public VideoAddServiceModel setVideoFile(MultipartFile videoFile) {
        this.videoFile = videoFile;
        return this;
    }
}
