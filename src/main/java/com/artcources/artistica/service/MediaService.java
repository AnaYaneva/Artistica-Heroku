package com.artcources.artistica.service;

import com.artcources.artistica.exception.ResourceNotFoundException;
import com.artcources.artistica.model.entity.MediaEntity;
import com.artcources.artistica.model.entity.OnlineWorkshopEntity;
import com.artcources.artistica.model.service.MediaAddServiceModel;
import com.artcources.artistica.repository.MediaRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;
    private final CloudinaryService cloudinaryService;

    public MediaService(MediaRepository mediaRepository, CloudinaryService cloudinaryService) {
        this.mediaRepository = mediaRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public MediaEntity save(MediaEntity videoEntity) {
        return this.mediaRepository.save(videoEntity);
    }

    public void deletePicture(Long picId) {
        MediaEntity picture = this.mediaRepository.findById(picId).orElseThrow(() -> new ResourceNotFoundException());
        this.cloudinaryService.delete(picture.getPublicId());
        this.mediaRepository.deleteById(picId);
    }

    public void getVideoEntity(OnlineWorkshopEntity workshop, MediaAddServiceModel mediaAddServiceModel) throws IOException {
        MediaEntity saved = getMediaEntity(mediaAddServiceModel);
        workshop.setVideo(saved);
    }

    public void getReferencePhotoEntity(OnlineWorkshopEntity workshop, MediaAddServiceModel mediaAddServiceModel) throws IOException {
        MediaEntity saved = getMediaEntity(mediaAddServiceModel);
        workshop.setReferencePhoto(saved);
    }



    public void getFinalPhotoEntity(OnlineWorkshopEntity workshop, MediaAddServiceModel mediaAddServiceModel) throws IOException {
        MediaEntity saved = getMediaEntity(mediaAddServiceModel);
        workshop.setFinalPhoto(saved);
    }

    private MediaEntity getMediaEntity(MediaAddServiceModel mediaAddServiceModel) throws IOException {
        CloudinaryMedia cloudinaryMedia = this.cloudinaryService.upload(mediaAddServiceModel.getFile());
        MediaEntity mediaEntity=new MediaEntity();
        mediaEntity.setName(mediaAddServiceModel.getName());
        mediaEntity.setPublicId(cloudinaryMedia.getPublicId());
        mediaEntity.setUrl(cloudinaryMedia.getUrl());
        MediaEntity saved = this.mediaRepository.save(mediaEntity);
        return saved;
    }
}
