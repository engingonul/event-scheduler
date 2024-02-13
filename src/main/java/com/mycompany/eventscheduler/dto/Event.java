package com.mycompany.eventscheduler.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.eventscheduler.enums.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {
    private String name;
    private String startTime;
    private Integer duration;
    @JsonIgnore
    private EventType eventType;
}
