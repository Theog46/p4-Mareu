package com.example.maru.service;

import com.example.maru.model.Meeting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings();



    void createMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    List<Meeting> generateMeeting();





    ArrayList<Meeting> getMeetingsFilteredByDate(Date date);

}
