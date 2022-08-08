package com.artcources.artistica.model.entity;

import com.artcources.artistica.model.enums.StatusEnum;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "online_workshops")
public class OnlineWorkshopEntity extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToOne
    private MediaEntity referencePhoto;

    @OneToOne
    private MediaEntity finalPhoto;

    @OneToOne
    private MediaEntity video;

    @ManyToOne
    private ExperienceLevelEntity experienceLevel;

    @ManyToOne
    private UserEntity mentor;

    @ManyToOne
    private WorkshopCategoryEntity category;

//    @Column(nullable = false)
    private Long duration;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToMany
    List<UserEntity> students = new ArrayList<>();

    public OnlineWorkshopEntity() {
    }

    public MediaEntity getFinalPhoto() {
        return finalPhoto;
    }

    public OnlineWorkshopEntity setFinalPhoto(MediaEntity finalPhoto) {
        this.finalPhoto = finalPhoto;
        return this;
    }

    public String getName() {
        return name;
    }

    public OnlineWorkshopEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OnlineWorkshopEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public MediaEntity getReferencePhoto() {
        return referencePhoto;
    }

    public OnlineWorkshopEntity setReferencePhoto(MediaEntity picture) {
        this.referencePhoto = picture;
        return this;
    }

    public MediaEntity getVideo() {
        return video;
    }

    public OnlineWorkshopEntity setVideo(MediaEntity video) {
        this.video = video;
        return this;
    }

    public ExperienceLevelEntity getExperienceLevel() {
        return experienceLevel;
    }

    public OnlineWorkshopEntity setExperienceLevel(ExperienceLevelEntity experienceLevel) {
        this.experienceLevel = experienceLevel;
        return this;
    }

    public UserEntity getMentor() {
        return mentor;
    }

    public OnlineWorkshopEntity setMentor(UserEntity mentor) {
        this.mentor = mentor;
        return this;
    }

    public WorkshopCategoryEntity getCategory() {
        return category;
    }

    public OnlineWorkshopEntity setCategory(WorkshopCategoryEntity category) {
        this.category = category;
        return this;
    }

    public Long getDuration() {
        return duration;
    }

    public OnlineWorkshopEntity setDuration(Long duration) {
        this.duration = duration;
        return this;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public OnlineWorkshopEntity setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }
}
