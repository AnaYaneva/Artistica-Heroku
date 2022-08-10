package com.artcources.artistica.model.service;

import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;
import com.artcources.artistica.model.enums.WorkshopCategoryEnum;

import java.util.ArrayList;
import java.util.List;

public class WorkshopsAllServiceModel {
    private String id;
    private String name;
    private String duration;
    private String finalUrl;
    private ExperienceLevelEnum experienceLevel;
    private UserEntity mentor;
    private WorkshopCategoryEnum category;
    List<UserEntity> students = new ArrayList<>();

    public WorkshopsAllServiceModel() {
    }

    public String getId() {
        return id;
    }

    public WorkshopsAllServiceModel setId(String id) {
        this.id = id;
        return this;
    }

    public List<UserEntity> getStudents() {
        return students;
    }

    public WorkshopsAllServiceModel setStudents(List<UserEntity> students) {
        this.students = students;
        return this;
    }

    public String getName() {
        return name;
    }

    public WorkshopsAllServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public WorkshopsAllServiceModel setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public String getFinalUrl() {
        return finalUrl;
    }

    public WorkshopsAllServiceModel setFinalUrl(String finalUrl) {
        this.finalUrl = finalUrl;
        return this;
    }

    public ExperienceLevelEnum getExperienceLevel() {
        return experienceLevel;
    }

    public WorkshopsAllServiceModel setExperienceLevel(ExperienceLevelEnum experienceLevel) {
        this.experienceLevel = experienceLevel;
        return this;
    }

    public UserEntity getMentor() {
        return mentor;
    }

    public WorkshopsAllServiceModel setMentor(UserEntity mentor) {
        this.mentor = mentor;
        return this;
    }

    public WorkshopCategoryEnum getCategory() {
        return category;
    }

    public WorkshopsAllServiceModel setCategory(WorkshopCategoryEnum category) {
        this.category = category;
        return this;
    }
}
