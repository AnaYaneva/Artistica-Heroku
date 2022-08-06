package com.artcources.artistica.model.view;

import com.artcources.artistica.model.entity.*;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.model.enums.StatusEnum;

import javax.persistence.*;

public class WorkshopDetailsViewModel {

    private String name;


    private String description;




    private ExperienceLevelEnum experienceLevel;


    private UserEntity mentor;


    private WorkshopCategoryEntity category;

    public WorkshopDetailsViewModel() {
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

    public WorkshopCategoryEntity getCategory() {
        return category;
    }

    public WorkshopDetailsViewModel setCategory(WorkshopCategoryEntity category) {
        this.category = category;
        return this;
    }
}
