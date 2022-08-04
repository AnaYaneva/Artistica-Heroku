package com.artcources.artistica.model.service;

import com.artcources.artistica.model.entity.CourseCategoryEntity;
import com.artcources.artistica.model.entity.ExperienceLevelEntity;
import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.VideoEntity;
import com.artcources.artistica.model.enums.CourseCategoryEnum;
import com.artcources.artistica.model.enums.StatusEnum;

import javax.persistence.*;

public class WorkshopAddServiceModel {

    private Long id;


    private String name;


    private String description;



    private VideoAddServiceModel video;


    private ExperienceLevelEntity experienceLevel;


    private UserEntity mentor;


    private CourseCategoryEnum category;



    private StatusEnum status;

    public WorkshopAddServiceModel() {
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

    public Long getId() {
        return id;
    }

    public WorkshopAddServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public VideoAddServiceModel getVideo() {
        return video;
    }

    public WorkshopAddServiceModel setVideo(VideoAddServiceModel video) {
        this.video = video;
        return this;
    }

    public ExperienceLevelEntity getExperienceLevel() {
        return experienceLevel;
    }

    public WorkshopAddServiceModel setExperienceLevel(ExperienceLevelEntity experienceLevel) {
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

    public CourseCategoryEnum getCategory() {
        return category;
    }

    public WorkshopAddServiceModel setCategory(CourseCategoryEnum category) {
        this.category = category;
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
