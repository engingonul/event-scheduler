package com.mycompany.eventscheduler.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Calendar {
    private List<Date> dateList;

    public Calendar() {
        this.dateList = new ArrayList<>();
    }

    public void addDate(Date date) {
        this.dateList.add(date);
    }

}
