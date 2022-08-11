package com.artcources.artistica.model.binding;

import com.artcources.artistica.model.enums.WorkshopCategoryEnum;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WorkshopAddBindingModel {

    @NotBlank(message = "Title is required!")
    @Size(min=5,max=25,message = "Title must be between 5 anf 25 characters.")
    private String name;

    @NotBlank(message = "Description is required!")
    @Size(min=5,max=500,message = "Description must be between 5 anf 500 characters.")
    private String description;

    @NotNull(message = "You must select a experience level")
    private ExperienceLevelEnum experienceLevel;

    @NotNull(message = "You must select a workshop category")
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
