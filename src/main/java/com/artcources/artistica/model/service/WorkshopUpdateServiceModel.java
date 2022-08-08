package com.artcources.artistica.model.service;

import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.WorkshopCategoryEntity;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;

public class WorkshopUpdateServiceModel {

    private String id;
    private String name;
    private String duration;
    private String finalUrl;
    private ExperienceLevelEnum experienceLevel;
    private UserEntity mentor;
    private WorkshopCategoryEntity category;


    public WorkshopUpdateServiceModel() {
    }

    public String getId() {
        return id;
    }

    public WorkshopUpdateServiceModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WorkshopUpdateServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public WorkshopUpdateServiceModel setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public String getFinalUrl() {
        return finalUrl;
    }

    public WorkshopUpdateServiceModel setFinalUrl(String finalUrl) {
        this.finalUrl = finalUrl;
        return this;
    }

    public ExperienceLevelEnum getExperienceLevel() {
        return experienceLevel;
    }

    public WorkshopUpdateServiceModel setExperienceLevel(ExperienceLevelEnum experienceLevel) {
        this.experienceLevel = experienceLevel;
        return this;
    }

    public UserEntity getMentor() {
        return mentor;
    }

    public WorkshopUpdateServiceModel setMentor(UserEntity mentor) {
        this.mentor = mentor;
        return this;
    }

    public WorkshopCategoryEntity getCategory() {
        return category;
    }

    public WorkshopUpdateServiceModel setCategory(WorkshopCategoryEntity category) {
        this.category = category;
        return this;
    }
}
