package com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data;

/**
 * Created by frank.blandon on 9/24/16.
 */
public class Drive {
    private String date;
    private Route route;

    public Drive(String date, Route route) {
        this.date = date;
        this.route = route;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "Date:" + date;
    }
}
