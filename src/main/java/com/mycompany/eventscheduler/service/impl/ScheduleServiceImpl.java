package com.mycompany.eventscheduler.service.impl;

import com.mycompany.eventscheduler.api.request.EventRequest;
import com.mycompany.eventscheduler.dto.Date;
import com.mycompany.eventscheduler.dto.Event;
import com.mycompany.eventscheduler.dto.Calendar;
import com.mycompany.eventscheduler.dto.Track;
import com.mycompany.eventscheduler.enums.EventType;
import com.mycompany.eventscheduler.enums.TrackType;
import com.mycompany.eventscheduler.service.ScheduleService;
import com.mycompany.eventscheduler.util.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mycompany.eventscheduler.util.Helper.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Override
    public Calendar create(List<EventRequest> eventRequestList) {
        if (eventRequestList.isEmpty()) {
            return null;
        }
        List<Event> eventList = getEvents(eventRequestList);
        return createSchedule(eventList);
    }

    private Calendar createSchedule(List<Event> eventList) {
        Calendar calendar = new Calendar();
        List<Event> commonEvents = eventList.stream().filter(o -> o.getEventType() == EventType.COMMON).toList();
        List<Event> lightningEvents = eventList.stream().filter(o -> o.getEventType() == EventType.LIGHTNING).toList();
        List<Track> trackList = new ArrayList<>();
        for (int i = 0; i < commonEvents.size(); i++) {
            int eventDuration = commonEvents.get(i).getDuration();
            int bestTrackIndex = 0;
            boolean createNewTrack = true;
            for (int j = 0; j < trackList.size(); j++) {
                int availableTrackSize = trackList.get(j).getAvailableSize();
                if (availableTrackSize >= eventDuration && availableTrackSize - eventDuration <= trackList.get(j).getTotalSize()) {
                    bestTrackIndex = j;
                    createNewTrack = false;
                }
            }

            if (createNewTrack) {
                Track track = new Track(getTrackType(trackList));
                track.addEvent(commonEvents.get(i));
                track.decreaseSize(eventDuration);
                trackList.add(track);
                commonEvents.get(i).setStartTime(calculateStartTime(track, eventDuration));
                addToDate(track, trackList, calendar);
            } else {
                trackList.get(bestTrackIndex).addEvent(commonEvents.get(i));
                trackList.get(bestTrackIndex).decreaseSize(eventDuration);
                commonEvents.get(i).setStartTime(calculateStartTime(trackList.get(bestTrackIndex), eventDuration));
            }
        }
        generateLunchPeriods(trackList);
        createLightningEvents(trackList, lightningEvents);
        createNetworkingEvents(trackList);

        return calendar;
    }

    private List<Event> getEvents(List<EventRequest> eventRequestList) {
        List<Event> eventList = new ArrayList<>();
        for (EventRequest item : eventRequestList) {
            Event event = new Event();
            event.setName(item.getName());
            event.setEventType(getEventType(item.getDuration().toLowerCase().trim()));
            event.setDuration(getDurationInt(item.getDuration().toLowerCase().trim(), event.getEventType()));
            eventList.add(event);
        }
        return eventList;
    }

    private void addToDate(Track track, List<Track> trackList, Calendar calendar) {
        if (track.getType() == TrackType.BEFORE_NOON) {
            Date date = new Date(String.format("DATE-%d", (trackList.size() / 2) + 1));
            date.appendTrack(track);
            calendar.addDate(date);
        } else {
            Date date = calendar.getDateList().get(calendar.getDateList().size() - 1);
            date.appendTrack(track);
        }
    }

    private void generateLunchPeriods(List<Track> trackList) {
        List<Track> morningTracks = trackList.stream().filter(o -> o.getType() == TrackType.BEFORE_NOON).toList();
        for (Track track : morningTracks) {
            Event event = new Event();
            event.setName("Lunch");
            event.setDuration(60);
            event.setStartTime("12:00PM");
            track.addEvent(event);
        }
    }

    private void createLightningEvents(List<Track> trackList, List<Event> lightningEventList) {
        for (Event event : lightningEventList) {
            Track availableTrack = getAvailableTrack(trackList, Constants.LIGHTNING_EVENT_DURATION);
            if (availableTrack != null) {
                availableTrack.addEvent(event);
                availableTrack.decreaseSize(Constants.LIGHTNING_EVENT_DURATION);
                event.setStartTime(calculateStartTime(availableTrack, Constants.LIGHTNING_EVENT_DURATION));
            }
        }
    }

    private void createNetworkingEvents(List<Track> trackList) {
        List<Track> noonTracks = trackList.stream().filter(o -> o.getType() == TrackType.AFTER_NOON).toList();
        for (Track track : noonTracks) {
            if (track.getAvailableSize() >= 0) {
                Event event = new Event();
                event.setName("Networking Event");
                if (track.getAvailableSize() >= Constants.NETWORKING_EVENT_MAX_DURATION) {
                    event.setStartTime("4:00PM");
                    event.setDuration(60);
                } else {
                    event.setStartTime(calculateStartTime(track, 0));
                    event.setDuration(track.getAvailableSize());
                }
                track.addEvent(event);
                track.decreaseSize(event.getDuration());
            }
        }
    }

}
