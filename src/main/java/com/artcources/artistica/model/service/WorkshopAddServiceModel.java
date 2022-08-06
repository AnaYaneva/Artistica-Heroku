package com.artcources.artistica.model.service;

import com.artcources.artistica.model.entity.ExperienceLevelEntity;
import com.artcources.artistica.model.entity.MediaEntity;
import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.WorkshopCategoryEntity;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.model.enums.StatusEnum;

import javax.persistence.*;

public class WorkshopAddServiceModel {

    private Long id;

    private String name;

    private String description;

    private MediaAddServiceModel referencePhoto;

    private MediaAddServiceModel finalPhoto;

    private MediaAddServiceModel video;

    private ExperienceLevelEnum experienceLevel;

    private UserEntity mentor;

    private WorkshopCategoryEnum category;

    private Long duration;

    private StatusEnum status;

    public WorkshopAddServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public WorkshopAddServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WorkshopAddServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WorkshopAddServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public MediaAddServiceModel getReferencePhoto() {
        return referencePhoto;
    }

    public WorkshopAddServiceModel setReferencePhoto(MediaAddServiceModel referencePhoto) {
        this.referencePhoto = referencePhoto;
        return this;
    }

    public MediaAddServiceModel getFinalPhoto() {
        return finalPhoto;
    }

    public WorkshopAddServiceModel setFinalPhoto(MediaAddServiceModel finalPhoto) {
        this.finalPhoto = finalPhoto;
        return this;
    }

    public MediaAddServiceModel getVideo() {
        return video;
    }

    public WorkshopAddServiceModel setVideo(MediaAddServiceModel video) {
        this.video = video;
        return this;
    }

    public ExperienceLevelEnum getExperienceLevel() {
        return experienceLevel;
    }

    public WorkshopAddServiceModel setExperienceLevel(ExperienceLevelEnum experienceLevel) {
        this.experienceLevel = experienceLevel;
        return this;
    }

    public UserEntity getMentor() {
        return mentor;
    }

    public WorkshopAddServiceModel setMentor(UserEntity mentor) {
        this.mentor = mentor;
        return this;
    }

    public WorkshopCategoryEnum getCategory() {
        return category;
    }

    public WorkshopAddServiceModel setCategory(WorkshopCategoryEnum category) {
        this.category = category;
        return this;
    }

    public Long getDuration() {
        return duration;
    }

    public WorkshopAddServiceModel setDuration(Long duration) {
        this.duration = duration;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public WorkshopAddServiceModel setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }
}
