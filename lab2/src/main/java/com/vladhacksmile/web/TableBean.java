package com.vladhacksmile.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class TableBean implements Serializable {
    private ArrayList<UserRequest> userRequests;

    public TableBean() {
        this.userRequests = new ArrayList<>();
    }

    public TableBean(ArrayList<UserRequest> userRequests) {
        this.userRequests = userRequests;
    }

    public ArrayList<UserRequest> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(ArrayList<UserRequest> userRequests) {
        this.userRequests = userRequests;
    }

    public void addRequest(UserRequest userRequest) {
        userRequests.add(userRequest);
    }

    @Override
    public String toString() {
        return "TableBean{" +
                "userRequests=" + userRequests +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableBean tableBean = (TableBean) o;
        return Objects.equals(userRequests, tableBean.userRequests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRequests);
    }
}
