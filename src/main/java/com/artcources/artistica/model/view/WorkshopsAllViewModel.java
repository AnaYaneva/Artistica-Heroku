package com.artcources.artistica.model.view;

import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.WorkshopCategoryEntity;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;

public class WorkshopsAllViewModel {
    private String id;
    private String name;
    private String duration;
    private String finalUrl;
    private ExperienceLevelEnum experienceLevel;
    private UserEntity mentor;
    private WorkshopCategoryEntity category;

    public WorkshopsAllViewModel() {
    }

    public String getId() {
        return id;
    }

    public WorkshopsAllViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WorkshopsAllViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public WorkshopsAllViewModel setDuration(Long duration) {
        this.duration = ""+duration/60+"h " + String.format("%02d",duration%60)+"m";
        return this;
    }

    public WorkshopsAllViewModel setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public String getFinalUrl() {
        return finalUrl;
    }

    public WorkshopsAllViewModel setFinalUrl(String finalUrl) {
        this.finalUrl = finalUrl;
        return this;
    }

    public ExperienceLevelEnum getExperienceLevel() {
        return experienceLevel;
    }

    public WorkshopsAllViewModel setExperienceLevel(ExperienceLevelEnum experienceLevel) {
        this.experienceLevel = experienceLevel;
        return this;
    }

    public UserEntity getMentor() {
        return mentor;
    }

    public WorkshopsAllViewModel setMentor(UserEntity mentor) {
        this.mentor = mentor;
        return this;
    }

    public WorkshopCategoryEntity getCategory() {
        return category;
    }

    public WorkshopsAllViewModel setCategory(WorkshopCategoryEntity category) {
        this.category = category;
        return this;
    }
}
