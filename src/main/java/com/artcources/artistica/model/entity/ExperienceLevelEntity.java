package com.artcources.artistica.model.entity;

import com.artcources.artistica.model.enums.ExperienceLevelEnum;

import javax.persistence.*;

@Entity
@Table(name ="experience_level")
public class ExperienceLevelEntity extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExperienceLevelEnum name;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String description;

    public ExperienceLevelEntity() {
    }

    public ExperienceLevelEnum getName() {
        return name;
    }

    public ExperienceLevelEntity setName(ExperienceLevelEnum experienceLevel) {
        this.name = experienceLevel;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ExperienceLevelEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}
