package com.artcources.artistica.model.entity;

import com.artcources.artistica.model.enums.WorkshopCategoryEnum;

import javax.persistence.*;

@Entity
@Table(name ="workshop_categories")
public class WorkshopCategoryEntity extends BaseEntity{
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkshopCategoryEnum name;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String description;

    public WorkshopCategoryEntity() {
    }

    public WorkshopCategoryEnum getName() {
        return name;
    }

    public WorkshopCategoryEntity setName(WorkshopCategoryEnum category) {
        this.name = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WorkshopCategoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}
