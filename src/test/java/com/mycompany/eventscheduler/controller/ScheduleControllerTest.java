package com.mycompany.eventscheduler.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.eventscheduler.api.request.EventRequest;
import com.mycompany.eventscheduler.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ScheduleController.class)
public class ScheduleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ScheduleService scheduleService;

    @Test
    void whenInvalidInput_EmptyRequest_thenReturn400() throws Exception {
        EventRequest input = new EventRequest();
        List<EventRequest> inputList = Collections.singletonList(input);

        mockMvc.perform(post("/schedule/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(inputList)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidInput_MissingInfo_thenReturn400() throws Exception {
        EventRequest input = new EventRequest();
        input.setName("");
        input.setDuration("30min");

        List<EventRequest> inputList = Collections.singletonList(input);

        mockMvc.perform(post("/schedule/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(inputList)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidInput_DurationError_thenReturn400() throws Exception {
        EventRequest input = new EventRequest();
        input.setName("Test Event");
        input.setDuration("30hour");

        List<EventRequest> inputList = Collections.singletonList(input);

        mockMvc.perform(post("/schedule/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(inputList)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenValidInput_thenReturn200() throws Exception {
        EventRequest input = new EventRequest();
        input.setName("Test Event");
        input.setDuration("30min");

        List<EventRequest> inputList = Collections.singletonList(input);

        mockMvc.perform(post("/schedule/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(inputList)))
                .andExpect(status().isOk());
    }
}
