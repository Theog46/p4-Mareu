package com.example.maru.service;

import com.example.maru.model.Meeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> mMeetings = MeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    @Override
    public void createMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
    }

    @Override
    public List<Meeting> generateMeeting() {

        mMeetings = MeetingGenerator.generateMeetings();
        return mMeetings;
    }



    @Override
    public ArrayList<Meeting> getMeetingsFilteredByDate(Date date) {

        ArrayList<Meeting> result = new ArrayList<>();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);

        for (int i = 0; i < mMeetings.size(); i++) {
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(mMeetings.get(i).getDate());
            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            if (sameDay) result.add(mMeetings.get(i));
        }
        return result;
    }
}
