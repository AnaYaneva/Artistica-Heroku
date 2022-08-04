package com.artcources.artistica.model.binding;

import com.artcources.artistica.model.entity.CourseCategoryEntity;
import com.artcources.artistica.model.entity.ExperienceLevelEntity;
import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.VideoEntity;
import com.artcources.artistica.model.enums.CourseCategoryEnum;
import com.artcources.artistica.model.enums.StatusEnum;

import javax.persistence.*;

public class WorkshopAddBindingModel {

    private String name;

    private String description;

    private VideoEntity video;

    private ExperienceLevelEntity experienceLevel;

    private CourseCategoryEnum category;

    public WorkshopAddBindingModel() {
    }

    public String getName() {
        return name;
    }

    public WorkshopAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WorkshopAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public VideoEntity getVideo() {
        return video;
    }

    public WorkshopAddBindingModel setVideo(VideoEntity video) {
        this.video = video;
        return this;
    }

    public ExperienceLevelEntity getExperienceLevel() {
        return experienceLevel;
    }

    public WorkshopAddBindingModel setExperienceLevel(ExperienceLevelEntity experienceLevel) {
        this.experienceLevel = experienceLevel;
        return this;
    }

    public CourseCategoryEnum getCategory() {
        return category;
    }

    public WorkshopAddBindingModel setCategory(CourseCategoryEnum category) {
        this.category = category;
        return this;
    }
}
