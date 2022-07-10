package com.artcources.artistica.model.entity;

import com.artcources.artistica.model.enums.CourseCategoryEnum;
import com.artcources.artistica.model.enums.UserRoleEnum;

import javax.persistence.*;

@Entity
@Table(name ="course_categories")
public class CourseCategoryEntity extends BaseEntity{
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseCategoryEnum category;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String description;

    public CourseCategoryEntity() {
    }

    public CourseCategoryEnum getCategory() {
        return category;
    }

    public CourseCategoryEntity setCategory(CourseCategoryEnum category) {
        this.category = category;
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
