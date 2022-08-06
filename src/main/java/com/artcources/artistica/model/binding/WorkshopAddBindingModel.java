package com.artcources.artistica.model.binding;

import com.artcources.artistica.model.entity.MediaEntity;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;

import javax.persistence.OneToOne;

public class WorkshopAddBindingModel {

    private String name;

    private String description;

    private MediaAddBindingModel referencePhoto;

    private MediaAddBindingModel finalPhoto;

    private MediaAddBindingModel video;

    private ExperienceLevelEnum experienceLevel;

    private WorkshopCategoryEnum category;

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

    public MediaAddBindingModel getReferencePhoto() {
        return referencePhoto;
    }

    public WorkshopAddBindingModel setReferencePhoto(MediaAddBindingModel referencePhoto) {
        this.referencePhoto = referencePhoto;
        return this;
    }

    public MediaAddBindingModel getFinalPhoto() {
        return finalPhoto;
    }

    public WorkshopAddBindingModel setFinalPhoto(MediaAddBindingModel finalPhoto) {
        this.finalPhoto = finalPhoto;
        return this;
    }

    public MediaAddBindingModel getVideo() {
        return video;
    }

    public WorkshopAddBindingModel setVideo(MediaAddBindingModel video) {
        this.video = video;
        return this;
    }

    public ExperienceLevelEnum getExperienceLevel() {
        return experienceLevel;
    }

    public WorkshopAddBindingModel setExperienceLevel(ExperienceLevelEnum experienceLevel) {
        this.experienceLevel = experienceLevel;
        return this;
    }

    public WorkshopCategoryEnum getCategory() {
        return category;
    }

    public WorkshopAddBindingModel setCategory(WorkshopCategoryEnum category) {
        this.category = category;
        return this;
    }
}
