package com.example.liz.digipa;

/**
 * Created by Charlene on 11/5/2014.
 */
public class Events {
    // events (id, title, description, start date, end date, start time, end time, location, category, high_pri)

    private long id;
    private String title, description, startDate, startTime, endDate, endTime, location, category; // should category be category_id?
    private int high_pri;

    public Events() {

    }

    public Events(String title, String description, String startDate, String startTime, String endDate, String endTime, String location, String category, int priority) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.location = location;
        this.category = category;
        this.high_pri = priority;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }
    public String getStartTime() {
        return startTime;
    }

    public String getEndDate() {
        return endDate;
    }
    public String getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }
    public int getPriority() {
        return high_pri;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description= description;
    }
    public void setStartDate(String startDate) {
        this.startDate= startDate;
    }
    public void setStartTime(String startTime) {
        this.startTime= startTime;
    }
    public void setEndDate(String endDate) {
        this.endDate= endDate;
    }
    public void setEndTime(String endTime) {
        this.endTime= endTime;
    }
    public void setLocation(String location) {
        this.location= location;
    }
    public void setCategory(String category) {
        this.category= category;
    }
    public void setPriority(int priority) {
        this.high_pri= priority;
    }
}
