package com.artcources.artistica.service;

import com.artcources.artistica.exception.ResourceNotFoundException;
import com.artcources.artistica.model.entity.OnlineWorkshopEntity;
import com.artcources.artistica.model.entity.VideoEntity;
import com.artcources.artistica.model.service.VideoAddServiceModel;
import com.artcources.artistica.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class VideoService{
    private final VideoRepository videoRepository;
    private final CloudinaryService cloudinaryService;

    public VideoService(VideoRepository videoRepository, CloudinaryService cloudinaryService) {
        this.videoRepository = videoRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public VideoEntity save(VideoEntity videoEntity) {
        return this.videoRepository.save(videoEntity);
    }

    public void deletePicture(Long picId) {
        VideoEntity picture = this.videoRepository.findById(picId).orElseThrow(() -> new ResourceNotFoundException());
        this.cloudinaryService.delete(picture.getPublicId());
        this.videoRepository.deleteById(picId);
    }

    public void getPictureEntity(OnlineWorkshopEntity wokrshop, VideoAddServiceModel videoAddServiceModel) throws IOException {
        CloudinaryVideo cloudinaryVideo = this.cloudinaryService.upload(videoAddServiceModel.getVideoFile());
        VideoEntity video=new VideoEntity();
        video.setName(videoAddServiceModel.getNameVideo());
        video.setPublicId(cloudinaryVideo.getPublicId());
        video.setUrl(cloudinaryVideo.getUrl());
        video.setWorkshop(wokrshop);
        VideoEntity savedVideo = this.videoRepository.save(video);
        wokrshop.setVideo(savedVideo);
    }
}
