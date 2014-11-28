package com.example.liz.digipa;

/**
 * Created by Charlene on 11/5/2014.
 */
public class Tasks {
    // // tasks (id title descrip, due date, category, high pri, is_complete

    private long _id;
    private String title, description, dueDate, category; // should category be category_id?
    private int high_pri, is_complete;

    public Tasks() {

    }

    public Tasks(String title, String description, String dueDate, String category, int priority, int complete) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.high_pri = priority;
        this.is_complete = complete;
    }

    public long getId() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getCategory() {
        return category;
    }
    public int getPriority() {
        return high_pri;
    }
    public int getComplete() {
        return is_complete;
    }

    public void setId(long id) {
        this._id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description= description;
    }
    public void setDueDate(String dueDate) {
        this.dueDate= dueDate;
    }
    public void setCategory(String category) {
        this.category= category;
    }
    public void setPriority(int priority) {
        this.high_pri= priority;
    }
    public void setComplete(int complete) {
        this.is_complete= complete;
    }
}
