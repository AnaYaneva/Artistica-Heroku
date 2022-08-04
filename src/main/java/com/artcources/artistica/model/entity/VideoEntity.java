package com.artcources.artistica.model.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "videos")
public class VideoEntity extends BaseMediaEntity {

    public VideoEntity() {
    }

    @OneToOne
    private OnlineWorkshopEntity workshop;

    public OnlineWorkshopEntity getWorkshop() {
        return workshop;
    }

    public VideoEntity setWorkshop(OnlineWorkshopEntity workshop) {
        this.workshop = workshop;
        return this;
    }

    public void clear() {
    }
}
