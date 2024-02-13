package com.mycompany.eventscheduler.service;

import com.mycompany.eventscheduler.api.request.EventRequest;
import com.mycompany.eventscheduler.dto.Calendar;

import java.util.List;

public interface ScheduleService {
    Calendar create(List<EventRequest> eventRequestList);
}
