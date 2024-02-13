package com.mycompany.eventscheduler.util;

import com.mycompany.eventscheduler.dto.Track;
import com.mycompany.eventscheduler.enums.EventType;
import com.mycompany.eventscheduler.enums.TrackType;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

public class Helper {

    public static EventType getEventType(String duration) {
        EventType eventType = EventType.COMMON;
        if (duration.contains("lightning")) {
            eventType = EventType.LIGHTNING;
        }
        return eventType;
    }

    public static Integer getDurationInt(String duration, EventType eventType) {
        Integer durationInt = null;
        if (eventType == EventType.COMMON) {
            String strValue = duration.replaceAll("[^0-9]", "");
            durationInt = Integer.parseInt(strValue);
        } else if (eventType == EventType.LIGHTNING) {
            durationInt = 5;
        }
        return durationInt;
    }

    public static TrackType getTrackType(List<Track> trackList) {
        TrackType trackType;
        if ((trackList.size() + 1) % 2 == 0) {
            trackType = TrackType.AFTER_NOON;
        } else {
            trackType = TrackType.BEFORE_NOON;
        }
        return trackType;
    }

    public static String calculateStartTime(Track track, Integer eventDuration) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("h:mm aa");
        String startTime;
        LocalTime time;
        if (track.getType() == TrackType.BEFORE_NOON) {
            startTime = Constants.MORNING_START;
        } else {
            startTime = Constants.NOON_START;
        }
        time = formatter.parseLocalTime(startTime).plusMinutes(track.getTotalSize() - track.getAvailableSize() - eventDuration);
        return sb.append(time.toString(formatter)).toString();
    }

    public static Track getAvailableTrack(List<Track> trackList, int duration) {
        return trackList.stream().filter(t -> t.getAvailableSize() >= duration).findFirst().orElse(null);
    }

}
