package com.mycompany.eventscheduler.controller;

import com.mycompany.eventscheduler.api.request.EventRequest;
import com.mycompany.eventscheduler.api.response.EventResponse;
import com.mycompany.eventscheduler.dto.Calendar;
import com.mycompany.eventscheduler.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@Validated
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @PostMapping("/create")
    public ResponseEntity<EventResponse> create(@RequestBody @Valid List<EventRequest> eventRequestList) {
        Calendar calendar = scheduleService.create(eventRequestList);
        EventResponse response = new EventResponse();
        response.setCalendar(calendar);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
