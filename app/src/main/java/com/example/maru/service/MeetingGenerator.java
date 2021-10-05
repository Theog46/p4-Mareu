package com.example.maru.service;

import android.graphics.Color;

import com.example.maru.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static android.graphics.Color.rgb;

public abstract class MeetingGenerator {

    private static int newColor;
    public static int getColor() {
        return newColor;
    }




    /* List of three default meetings */

    private static int color1 = Color.rgb(199, 155, 151);
    private static int color2 = Color.rgb(106, 150, 105);

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
      new Meeting(1, color1, "Peach","Réunion A", new Date(1626557205000L),"14h00", "maxime@lamzone.com, alex@lamzone.com"),

      new Meeting(2, color2, "Mario","Réunion B", new Date(1626557205000L),"16h00","paul@lamzone.com, viviane@lamzone.com"),

      new Meeting(3, color2,"Luigi", "Réunion C", new Date(1626384405000L),"18h00","amandine@lamzone.com, luc@lamzone.com")
    );


    /* Create a new meeting */

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

    public static int generateRandomColor() {
        newColor = rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
        return newColor;
    }


}

