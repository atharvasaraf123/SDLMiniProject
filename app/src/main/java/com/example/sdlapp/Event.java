package com.example.sdlapp;

import java.util.HashMap;

public class Event {

    private String title;
    private String desc;
    private String date;
    private String time;
    private String venue;
    String uid;
    private HashMap<String,Boolean>dept;

    public Event(String name, String desc, String date, String time, String venue, HashMap<String, Boolean> dept, String userEmail) {
        this.title = name;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.dept = dept;
        this.uid = userEmail;
    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public HashMap<String, Boolean> getDept() {
        return dept;
    }

    public void setDept(HashMap<String, Boolean> dept) {
        this.dept = dept;
    }
}
