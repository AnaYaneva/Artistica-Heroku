package com.artcources.artistica.model.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@MappedSuperclass
public abstract class BaseWokrshopEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

//    @OneToMany(mappedBy = "workshop", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
//    private List<PictureEntity> pictures;

    @ManyToOne
    private ExperienceLevelEntity experienceLevel;

    @ManyToOne
    private UserEntity mentor;

    private Long duration;

    public BaseWokrshopEntity() {
    }

    public String getName() {
        return name;
    }

    public BaseWokrshopEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BaseWokrshopEntity setDescription(String description) {
        this.description = description;
        return this;
    }
//
//    public List<PictureEntity> getPictures() {
//        return pictures;
//    }
//
//    public BaseWokrshopEntity setPictures(List<PictureEntity> pictures) {
//        this.pictures = pictures;
//        return this;
//    }

    public ExperienceLevelEntity getExperienceLevel() {
        return experienceLevel;
    }

    public BaseWokrshopEntity setExperienceLevel(ExperienceLevelEntity experienceLevel) {
        this.experienceLevel = experienceLevel;
        return this;
    }

    public UserEntity getMentor() {
        return mentor;
    }

    public BaseWokrshopEntity setMentor(UserEntity mentor) {
        this.mentor = mentor;
        return this;
    }

    public Long getDuration() {
        return duration;
    }

    public BaseWokrshopEntity setDuration(Long duration) {
        this.duration = duration;
        return this;
    }
}
