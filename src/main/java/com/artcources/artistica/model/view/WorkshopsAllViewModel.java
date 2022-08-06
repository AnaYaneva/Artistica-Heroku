package com.artcources.artistica.model.view;

public class WorkshopsAllViewModel {
    private String id;
    private String name;
    private String duration;

    private MediaViewModel pictures;

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

    public String getDuration() {
        return duration;
    }

    public WorkshopsAllViewModel setDuration(Long duration) {
        this.duration = ""+duration/60+"h " + String.format("%02d",duration%60)+"m";
        return this;
    }
}
