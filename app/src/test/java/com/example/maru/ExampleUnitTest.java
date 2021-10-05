package com.example.maru;

import com.example.maru.di.DI;
import com.example.maru.model.Meeting;
import com.example.maru.service.MeetingApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class ExampleUnitTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingWithSuccess() {
        List<Meeting> meetingList = service.getMeetings();
        int listSize = meetingList.size();
        assertEquals(3, listSize);
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void addMeetingWithSuccess() {
        Meeting newMeeting = new Meeting(4, 455555, "Wario", "Réunion D", new Date(1626557205000L), "20:55", "tony.martinez@amazon.fr, marc.carl@amazon.fr");
        service.createMeeting(newMeeting);
        assertTrue(service.getMeetings().contains(newMeeting));
    }

    @Test
    public void FilterByDate() {
        List<Meeting> meeting = service.getMeetings();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, 07, 15);
        List<Meeting> meetingsFiltered = service.getMeetingsFilteredByDate(new Date(1626557205000L));
        int MeetingSelected = meetingsFiltered.size();
        assertEquals(2, MeetingSelected);
    }

    @Test
    public void FilterNewMeetingByDate() {
        Meeting newMeeting = new Meeting(4, 455555, "Wario", "Réunion D", new Date(1626557205000L), "20:55", "tony.martinez@amazon.fr, marc.carl@amazon.fr");
        service.createMeeting(newMeeting);
        assertTrue(service.getMeetings().contains(newMeeting));
        List<Meeting> meeting = service.getMeetings();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, 07, 15);
        List<Meeting> meetingsFiltered = service.getMeetingsFilteredByDate(new Date(1626557205000L));
        int MeetingSelected = meetingsFiltered.size();
        assertEquals(3, MeetingSelected);
    }

}