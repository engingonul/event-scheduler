package com.mycompany.eventscheduler.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Date {
    private String name;
    private List<Track> trackList;

    public Date (String name) {
        this.name = name;
        this.trackList = new ArrayList<>();
    }
    public void appendTrack(Track track) {
        this.trackList.add(track);
    }
}
