package com.artcources.artistica.model.entity;

import com.artcources.artistica.model.enums.StatusEnum;

import javax.persistence.*;

@Entity
@Table(name = "online_workshops")
public class OnlineWorkshopEntity extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

//    @OneToMany(mappedBy = "workshop", fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
//    private List<PictureEntity> pictures;

    @OneToOne
    private VideoEntity video;

    @ManyToOne
    private ExperienceLevelEntity experienceLevel;

    @ManyToOne
    private UserEntity mentor;

    @ManyToOne
    private CourseCategoryEntity category;


    @Enumerated(EnumType.STRING)
    private StatusEnum status;


    public OnlineWorkshopEntity() {
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

    public VideoEntity getVideo() {
        return video;
    }

    public OnlineWorkshopEntity setVideo(VideoEntity video) {
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

    public CourseCategoryEntity getCategory() {
        return category;
    }

    public OnlineWorkshopEntity setCategory(CourseCategoryEntity category) {
        this.category = category;
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
