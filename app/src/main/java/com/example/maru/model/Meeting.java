package com.example.maru.model;

import java.io.Serializable;
import java.util.Date;

public class Meeting implements Serializable {

    /* ID */
    private long id;

    private int color;

    private String subject;

    private String place;

    private Date mDate;

    private String mTime;

    private String emails;


    public Meeting(long id, int color, String subject, String place, Date date, String time, String emails) {
        this.id = id;
        this.color = color;
        this.subject = subject;
        this.place = place;
        mDate = date;
        this.mTime = time;
        this.emails = emails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getHour() {
        return mTime;
    }

    public void setHour(String time) {
        this.mTime = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public String getEmails() { return emails; }

    public void setEmails(String emails) { this.emails = emails; }

}
