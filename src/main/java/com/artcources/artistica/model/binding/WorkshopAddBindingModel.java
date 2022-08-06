package com.artcources.artistica.model.binding;

import com.artcources.artistica.model.entity.VideoEntity;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;

public class WorkshopAddBindingModel {

    private String name;

    private String description;

    private VideoEntity video;

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

    public VideoEntity getVideo() {
        return video;
    }

    public WorkshopAddBindingModel setVideo(VideoEntity video) {
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
