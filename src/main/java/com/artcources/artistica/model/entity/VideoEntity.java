package com.artcources.artistica.model.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "videos")
public class VideoEntity extends BaseMediaEntity {

    public VideoEntity() {
    }


    public void clear() {
    }
}
