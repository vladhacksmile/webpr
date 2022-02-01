package com.vladhacksmile.lab3;

import javax.persistence.*;

@Entity
@Table (name = "requests")
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private int id;
    @Column(name = "execution")
    private float executionTime;
    @Column(name = "requestdate", length = -1)
    private String date;
    @Column(name = "x")
    private float x;
    @Column(name = "y")
    private float y;
    @Column(name = "r")
    private float r;
    @Column(name = "belong")
    private boolean belong;

    public UserRequest(float executionTime, String date, float x, float y, float r, boolean belong) {
        this.executionTime = executionTime;
        this.date = date;
        this.x = x;
        this.y = y;
        this.r = r;
        this.belong = belong;
    }

    public UserRequest() {

    }

    public float getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(float executionTime) {
        this.executionTime = executionTime;
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

// <property name="connection.username">s307405</property>
// <property name="connection.password">hji837</property>