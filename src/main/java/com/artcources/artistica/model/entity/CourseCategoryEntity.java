package com.artcources.artistica.model.entity;

import com.artcources.artistica.model.enums.CourseCategoryEnum;

import javax.persistence.*;

@Entity
@Table(name ="course_categories")
public class CourseCategoryEntity extends BaseEntity{
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseCategoryEnum name;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String description;

    public CourseCategoryEntity() {
    }

    public CourseCategoryEnum getName() {
        return name;
    }

    public CourseCategoryEntity setName(CourseCategoryEnum category) {
        this.name = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CourseCategoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}
