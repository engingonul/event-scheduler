package com.mycompany.eventscheduler.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.eventscheduler.enums.TrackType;
import com.mycompany.eventscheduler.util.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Track {
    @JsonIgnore
    private TrackType type;
    @JsonIgnore
    private Integer totalSize;
    @JsonIgnore
    private Integer availableSize;
    private List<Event> eventList;

    public Track(TrackType type) {
        this.type = type;
        this.totalSize = type == TrackType.BEFORE_NOON ? Constants.BEFORE_NOON_TRACK_SIZE : Constants.AFTER_NOON_TRACK_SIZE;
        this.availableSize = type == TrackType.BEFORE_NOON ? Constants.BEFORE_NOON_TRACK_SIZE : Constants.AFTER_NOON_TRACK_SIZE;
        this.eventList = new ArrayList<>();
    }

    public void addEvent(Event event) {
        this.eventList.add(event);
    }

    public void decreaseSize(Integer duration) {
        this.availableSize -= duration;
    }
}
