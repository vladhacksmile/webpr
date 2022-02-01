package com.vladhacksmile.lab3;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserRequest {
    private float executionTime;
    private Date requestDate;
    private String date;
    private float x;
    private float y;
    private float r;
    private boolean belong;

    public UserRequest(float executionTime, Date requestDate, float x, float y, float r, boolean belong) {
        this.executionTime = executionTime;
        this.requestDate = requestDate;
        this.date = new SimpleDateFormat().format(requestDate);
        this.x = x;
        this.y = y;
        this.r = r;
        this.belong = belong;
    }

    public UserRequest(float executionTime, String date, float x, float y, float r, boolean belong) {
        this.executionTime = executionTime;
        this.requestDate = new Date();
        this.date = date;
        this.x = x;
        this.y = y;
        this.r = r;
        this.belong = belong;
    }

    public float getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(float executionTime) {
        this.executionTime = executionTime;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public boolean isBelong() {
        return belong;
    }

    public void setBelong(boolean belong) {
        this.belong = belong;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
