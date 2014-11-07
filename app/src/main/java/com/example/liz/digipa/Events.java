package com.example.liz.digipa;

/**
 * Created by Charlene on 11/5/2014.
 */
public class Events {
    // events (id, title, description, start date, end date, star ttime, end time, location, category, high_pri)

    private long id;
    private String title, description, startDateTime, endDateTime, location, category; // should category be category_id?
    private int high_pri;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
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
    public void setStartDateTime(String startDateTime) {
        this.startDateTime= startDateTime;
    }
    public void setEndDateTime(String endDateTime) {
        this.endDateTime= endDateTime;
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
