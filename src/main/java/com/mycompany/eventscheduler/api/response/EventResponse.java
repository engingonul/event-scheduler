package com.mycompany.eventscheduler.api.response;

import com.mycompany.eventscheduler.dto.Calendar;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventResponse {
    private Calendar calendar;
}
