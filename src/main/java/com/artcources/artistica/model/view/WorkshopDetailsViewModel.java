package com.artcources.artistica.model.view;

import com.artcources.artistica.model.entity.*;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.model.enums.StatusEnum;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;

import javax.persistence.*;

public class WorkshopDetailsViewModel {

    private Long id;
    private String name;
    private String description;
    private ExperienceLevelEnum experienceLevel;
    private UserEntity mentor;
    private WorkshopCategoryEnum category;
    private String referenceUrl;
    private String finalUrl;
    private String videoUrl;

    public WorkshopDetailsViewModel() {
    }

    public Long getId() {
        return id;
    }

    public WorkshopDetailsViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WorkshopDetailsViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WorkshopDetailsViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public ExperienceLevelEnum getExperienceLevel() {
        return experienceLevel;
    }

    public WorkshopDetailsViewModel setExperienceLevel(ExperienceLevelEnum experienceLevel) {
        this.experienceLevel = experienceLevel;
        return this;
    }

    public UserEntity getMentor() {
        return mentor;
    }

    public WorkshopDetailsViewModel setMentor(UserEntity mentor) {
        this.mentor = mentor;
        return this;
    }

    public WorkshopCategoryEnum getCategory() {
        return category;
    }

    public WorkshopDetailsViewModel setCategory(WorkshopCategoryEnum category) {
        this.category = category;
        return this;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public WorkshopDetailsViewModel setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
        return this;
    }

    public String getFinalUrl() {
        return finalUrl;
    }

    public WorkshopDetailsViewModel setFinalUrl(String finalUrl) {
        this.finalUrl = finalUrl;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public WorkshopDetailsViewModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }
}
