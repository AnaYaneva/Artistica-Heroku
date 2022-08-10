package com.artcources.artistica.model.view;

import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.entity.WorkshopCategoryEntity;
import com.artcources.artistica.model.enums.ExperienceLevelEnum;

import java.util.ArrayList;
import java.util.List;

public class WorkshopsAllViewModel {
    private String id;
    private String name;
    private String finalUrl;
    private ExperienceLevelEnum experienceLevel;
    private UserEntity mentor;
    private WorkshopCategoryEntity category;

    List<UserEntity> students = new ArrayList<>();

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

    public List<UserEntity> getStudents() {
        return students;
    }

    public WorkshopsAllViewModel setStudents(List<UserEntity> students) {
        this.students = students;
        return this;
    }
}
