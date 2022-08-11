package com.artcources.artistica.model.binding;

import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WorkshopUpdateBindingModel {
    private Long id;

    @NotBlank(message = "Title is required!")
    @Size(min=5,max=25,message = "Title must be between 5 anf 25 characters.")
    private String name;

    @NotBlank(message = "Description is required!")
    @Size(min=5,max=500,message = "Description must be between 5 anf 500 characters.")
    private String description;

    @NotNull(message = "You must select a experience level")
    private ExperienceLevelEnum experienceLevel;
    private UserEntity mentor;

    @NotNull(message = "You must select a workshop category")
    private WorkshopCategoryEnum category;
    private String referenceUrl;
    private String finalUrl;
    private String videoUrl;

    public WorkshopUpdateBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public WorkshopUpdateBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WorkshopUpdateBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WorkshopUpdateBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public ExperienceLevelEnum getExperienceLevel() {
        return experienceLevel;
    }

    public WorkshopUpdateBindingModel setExperienceLevel(ExperienceLevelEnum experienceLevel) {
        this.experienceLevel = experienceLevel;
        return this;
    }

    public UserEntity getMentor() {
        return mentor;
    }

    public WorkshopUpdateBindingModel setMentor(UserEntity mentor) {
        this.mentor = mentor;
        return this;
    }

    public WorkshopCategoryEnum getCategory() {
        return category;
    }

    public WorkshopUpdateBindingModel setCategory(WorkshopCategoryEnum category) {
        this.category = category;
        return this;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public WorkshopUpdateBindingModel setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
        return this;
    }

    public String getFinalUrl() {
        return finalUrl;
    }

    public WorkshopUpdateBindingModel setFinalUrl(String finalUrl) {
        this.finalUrl = finalUrl;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public WorkshopUpdateBindingModel setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }
}
