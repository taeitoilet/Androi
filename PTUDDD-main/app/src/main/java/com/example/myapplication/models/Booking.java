package com.example.myapplication.models;

public class Booking {
    private int id;
    private String date;
    private String time;
    private String content;
    private String status;
    private double rating;
    private int userId;

    public Booking(){

    }
    public Booking(int id, String date, String time, String content, String status, double rating, int userId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.content = content;
        this.status = status;
        this.rating = rating;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
