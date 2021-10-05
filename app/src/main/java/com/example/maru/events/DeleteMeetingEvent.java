package com.example.maru.events;

import com.example.maru.model.Meeting;

public class DeleteMeetingEvent {
    public Meeting meeting;

    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
